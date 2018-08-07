package com.ml.vulferbetsystem.service;

import com.ml.vulferbetsystem.componet.DroughtWeatherCalculator;
import com.ml.vulferbetsystem.componet.PressureAndTemperatureWeatherCalculator;
import com.ml.vulferbetsystem.componet.RainWeatherCalculator;
import com.ml.vulferbetsystem.domain.ConfigParam;
import com.ml.vulferbetsystem.domain.ConfigParamConstants;
import com.ml.vulferbetsystem.domain.Planet;
import com.ml.vulferbetsystem.domain.PlanetMovement;
import com.ml.vulferbetsystem.domain.Point;
import com.ml.vulferbetsystem.domain.Weather;
import com.ml.vulferbetsystem.domain.WeatherSummary;
import com.ml.vulferbetsystem.domain.WeatherType;
import com.ml.vulferbetsystem.dto.PlanetDTO;
import com.ml.vulferbetsystem.dto.WeatherDTO;
import com.ml.vulferbetsystem.error.ErrorType;
import com.ml.vulferbetsystem.error.StatusCodeException;
import com.ml.vulferbetsystem.repositories.ConfigParamRepository;
import com.ml.vulferbetsystem.repositories.PlanetMovementRepository;
import com.ml.vulferbetsystem.repositories.PlanetRepository;
import com.ml.vulferbetsystem.repositories.weather.WeatherRepositoryWrapper;
import com.ml.vulferbetsystem.utils.DateUtils;
import com.ml.vulferbetsystem.utils.GeometryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class WeatherService {
    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);
    @Autowired
    private WeatherRepositoryWrapper weatherRepository;
    @Autowired
    private PlanetRepository planetRepository;
    @Autowired
    private ConfigParamRepository configParamRepository;
    @Autowired
    private PlanetMovementRepository planetMovementRepository;
    @Autowired
    @Qualifier("rainWeatherTriangleCalculator")
    private RainWeatherCalculator rainWeatherCalculator;
    @Autowired
    @Qualifier("droughtWeatherStraightCalculator")
    private DroughtWeatherCalculator droughtWeatherCalculator;
    @Autowired
    @Qualifier("pressureAndTemperatureWeatherStraightCalculator")
    private PressureAndTemperatureWeatherCalculator pressAndTempWeatherCalculator;

    public WeatherDTO getWeatherByDay(int days) {

        Weather byWeatherDate = weatherRepository.findByWeatherDate(days);
        if (byWeatherDate == null) {
            throw new StatusCodeException(HttpStatus.NOT_FOUND, ErrorType.WEATHER_NOT_FOUND);
        } else {
            return new WeatherDTO(days, byWeatherDate.getWeatherType());
        }
    }

    public WeatherDTO getWeatherAndPlanetByDay(int days) {

        Weather byWeatherDate = weatherRepository.findByWeatherDate(days);
        if (byWeatherDate == null) {
            throw new StatusCodeException(HttpStatus.NOT_FOUND, ErrorType.WEATHER_NOT_FOUND);
        } else {
            List<PlanetMovement> planetPositions = planetMovementRepository.findAllByPositionDate(days);
            List<PlanetDTO> planetsDTO = planetPositions.stream().map(pp -> new PlanetDTO(pp.getPlanet().getName(),
                    GeometryUtils.getCartesianCoordinatesFromPolar(pp.getPlanet().getSunDistance(), pp.getPositionAngle())))
                    .collect(Collectors.toList());
            return new WeatherDTO(days, byWeatherDate.getWeatherType(), planetsDTO);
        }
    }

    @Transactional
    public void calculateWeather() {
        weatherRepository.setBusy(true);
        StopWatch stopWatch = new StopWatch();
        try {
            log.info("****Calculando el clima");
            stopWatch.start();
            List<Planet> planets = planetRepository.findAll();
            cleanWeather(planets);
            ConfigParam periodToProcess = configParamRepository.findByName(
                    ConfigParamConstants.PERIOD_TO_PROCESS_IN_YEARS.name());
            long daysToProcess = getDaysToProcess(periodToProcess);

            log.info("Total days to process {} ", daysToProcess);

            List<Point> points = new ArrayList<>();

            int angulePosition;

            List<Weather> weathers = new ArrayList<>();
            java.util.Date dayWeather;
            double maxPerimeter = 0;
            double auxPerimeter;
            Weather newWth;

            for (int i = 1; i <= daysToProcess; i++) {
                dayWeather = java.sql.Date.valueOf(LocalDate.now().plusDays(i));
                //Se guardan los movimientos de los planetas y se obtienen los puntos x e y de los mismos
                for (Planet p : planets) {
                    angulePosition = GeometryUtils.getAnguleByVelocityAndTimes(p.getLastPosition(),
                            p.getAngularVelocity(), i);
                    p.getMovements().add(new PlanetMovement(angulePosition, p, dayWeather));
                    points.add(GeometryUtils.getCartesianCoordinatesFromPolar(p.getSunDistance(), angulePosition));
                }
                //Se detemina el tipo de clima
                if (rainWeatherCalculator.isRainWeather(points)) {
                    //TODO: mejorar
                    auxPerimeter = GeometryUtils.getPerimeterFromTriangle(points.get(0), points.get(1), points.get(2));
                    if (maxPerimeter < auxPerimeter && maxPerimeter == 0) {
                        maxPerimeter = auxPerimeter;
                        newWth = new Weather(WeatherType.MAX_RAIN, dayWeather);
                        weathers.add(newWth);
                    } else if (maxPerimeter < auxPerimeter) {
                        weathers.stream()
                                .filter(wt -> wt.getWeatherType().equals(WeatherType.MAX_RAIN))
                                .findAny()
                                .orElse(null)
                                .setWeatherType(WeatherType.RAIN);
                        newWth = new Weather(WeatherType.MAX_RAIN, dayWeather);
                        weathers.add(newWth);
                    } else {
                        newWth = new Weather(WeatherType.RAIN, dayWeather);
                        weathers.add(newWth);
                    }
                } else if (droughtWeatherCalculator.isDroughtWeather(points)) {
                    newWth = new Weather(WeatherType.DROUGHT, dayWeather);
                    weathers.add(newWth);
                } else if (pressAndTempWeatherCalculator.isPressureAndTempWeather(points)) {
                    newWth = new Weather(WeatherType.PRESSURE_AND_TEMPERATURE, dayWeather);
                    weathers.add(newWth);
                } else {
                    newWth = new Weather(WeatherType.NORMAL, dayWeather);
                    weathers.add(newWth);
                }
                WeatherSummary.addWeather(newWth);
                points.clear();
            }
            planets.forEach(p -> p.setLastPosition(p.getMovements().get(p.getMovements().size() - 1).getPositionAngle()));
            planetRepository.saveAll(planets);
            log.info("TOTAL de array weather {}", weathers.size());
            weatherRepository.saveAll(weathers);
            WeatherSummary.printSummary();

        } catch (Exception e) {
            log.error("Ocurrio un Error ", e);
        } finally {
            weatherRepository.setBusy(false);
            WeatherSummary.clearValues();
            stopWatch.stop();
            log.info("****Clima calculado en {} segundos", stopWatch.getTotalTimeSeconds());
        }
    }

    private void cleanWeather(List<Planet> planets) {
        weatherRepository.deleteAll();
        planets.forEach(planet -> planet.getMovements().clear());
    }


    private static long getDaysToProcess(ConfigParam periodToProcess) {
        return DateUtils.getDaysBetweenTodayAndOtherDate(getFuturePeriodDate(periodToProcess));
    }

    private static LocalDate getFuturePeriodDate(ConfigParam periodToProcess) {
        return LocalDate.now().plusYears(Long.parseLong(periodToProcess.getValue()));
    }


}
