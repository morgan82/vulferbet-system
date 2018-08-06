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
import com.ml.vulferbetsystem.utils.GeometryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

            ConfigParam fullWeatherProcessing = configParamRepository.findByName(
                    ConfigParamConstants.FULL_WEATHER_PROCESSING.name());
            if (fullWeatherProcessing != null) {
                if (Boolean.valueOf(fullWeatherProcessing.getValue())) {

                    ConfigParam periodToProcess = configParamRepository.findByName(
                            ConfigParamConstants.PERIOD_TO_PROCESS_IN_YEARS.name());

                    long daysToProcess = getTotalDaysToProcess(Integer.parseInt(periodToProcess.getValue()));
                    log.info("Total days to process {} ", daysToProcess);

                    List<Planet> planets = planetRepository.findAll();
                    List<Point> points = new ArrayList<>();

                    int angulePosition;

                    List<Weather> weathers = new ArrayList<>();
                    java.util.Date dayWeather;
                    double maxPerimeter = 0;
                    double auxPerimeter;
                    Weather w;

                    for (int i = 1; i <= daysToProcess; i++) {
                        dayWeather = java.sql.Date.valueOf(LocalDate.now().plusDays(i));
                        //Se guardan los movimientos de los planetas y se obtienen los puntos x e y de los mismos
                        for (Planet p : planets) {
                            angulePosition = GeometryUtils.getAnguleByVelocityAndTimes(p.getInitialPosition(),
                                    p.getAngularVelocity(), i);
                            p.getMovements().add(new PlanetMovement(angulePosition, p, dayWeather));
                            points.add(GeometryUtils.getCartesianCoordinatesFromPolar(p.getSunDistance(), angulePosition));
                        }
                        //Se detemina el tipo de clima
                        if (rainWeatherCalculator.isRainWeather(points, i)) {
                            auxPerimeter = GeometryUtils.getPerimeterFromTriangle(points.get(0), points.get(1), points.get(2));
                            if (maxPerimeter < auxPerimeter && maxPerimeter == 0) {
                                maxPerimeter = auxPerimeter;
                                w = new Weather(WeatherType.MAX_RAIN, dayWeather);
                                weathers.add(w);
                            } else if (maxPerimeter < auxPerimeter) {
                                weathers.stream()
                                        .filter(w -> w.getWeatherType().equals(WeatherType.MAX_RAIN))
                                        .findAny()
                                        .orElse(null)
                                        .setWeatherType(WeatherType.RAIN);
                                w = new Weather(WeatherType.MAX_RAIN, dayWeather);
                                weathers.add(w);
                            } else {
                                w = new Weather(WeatherType.RAIN, dayWeather);
                                weathers.add(w);
                                WeatherSummary.addWeather(w);
                            }
//                            log.info("\n" + WeatherType.RAIN + "-> " + "\n dayWeather-> " + dayWeather + points.stream().map(Point::toString).collect(Collectors.joining("\n", "\n", "\n")));
                        } else if (droughtWeatherCalculator.isDroughtWeather(points, i)) {
                            w = new Weather(WeatherType.DROUGHT, dayWeather);
                            weathers.add(w);
//                            log.info("\n" + WeatherType.DROUGHT + "-> " + "\n dayWeather-> " + dayWeather + points.stream().map(Point::toString).collect(Collectors.joining("\n", "\n", "\n")));
                        } else if (pressAndTempWeatherCalculator.isPressureAndTempWeather(points, i)) {
                            w = new Weather(WeatherType.PRESSURE_AND_TEMPERATURE, dayWeather);
                            weathers.add(w);
//                            log.info("\n" + WeatherType.PRESSURE_AND_TEMPERATURE + "-> " + "\n dayWeather-> " + dayWeather + points.stream().map(Point::toString).collect(Collectors.joining("\n", "\n", "\n")));
                        } else {
                            w = new Weather(WeatherType.NORMAL, dayWeather);
                            weathers.add(w);
//                            log.info((WeatherType.NORMAL + "-> " + points.stream().map(Point::toString).collect(Collectors.joining())));
                        }
                        WeatherSummary.addWeather(w);
                        points.clear();
                    }
                    planetRepository.saveAll(planets);
                    weatherRepository.saveAll(weathers);
                    printWeatherSummary(weathers);
                } else {
                    //TODO:CALCULAR SOLO UN DIA MAS
                }
            } else {
                log.error("Falta Parametro de Inicializacion para calcular el clima");
            }
        } catch (Exception e) {
            log.error("Ocurrio un Error ", e);
        } finally {
            weatherRepository.setBusy(false);
            WeatherSummary.clearValues();
            stopWatch.stop();
            log.info("****Clima calculado en {} segundos", stopWatch.getTotalTimeSeconds());
        }
    }

    private static void printWeatherSummary(List<Weather> weathers) {

    }

    private long getTotalDaysToProcess(int years) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime maxTimeToProcess = today.plusYears(years);
        Duration duration = Duration.between(today, maxTimeToProcess);

        return Math.abs(duration.toDays());
    }


}
