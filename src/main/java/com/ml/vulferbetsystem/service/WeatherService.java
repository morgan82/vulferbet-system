package com.ml.vulferbetsystem.service;

import com.ml.vulferbetsystem.domain.ConfigParam;
import com.ml.vulferbetsystem.domain.ConfigParamConstants;
import com.ml.vulferbetsystem.domain.Planet;
import com.ml.vulferbetsystem.domain.PlanetMovement;
import com.ml.vulferbetsystem.domain.Point;
import com.ml.vulferbetsystem.domain.Weather;
import com.ml.vulferbetsystem.domain.WeatherType;
import com.ml.vulferbetsystem.dto.WeatherDTO;
import com.ml.vulferbetsystem.error.ErrorType;
import com.ml.vulferbetsystem.error.StatusCodeException;
import com.ml.vulferbetsystem.repositories.ConfigParamRepository;
import com.ml.vulferbetsystem.repositories.PlanetRepository;
import com.ml.vulferbetsystem.repositories.weather.WeatherRepositoryWrapper;
import com.ml.vulferbetsystem.utils.GeometryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class WeatherService {
    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);
    @Autowired
    private WeatherRepositoryWrapper weatherRepository;
    @Autowired
    private PlanetRepository planetRepository;
    @Autowired
    private ConfigParamRepository configParamRepository;
    private RainWeatherCalculator rainWeatherCalculator;
    @Autowired
    @Qualifier("droughtWeatherStraightCalculator")
    private DroughtWeatherCalculator droughtWeatherCalculator;
    private PressureAndTemperatureWeatherCalculator pressAndTempWeatherCalculator;

    public WeatherDTO getWeatherByDay(int days) {

        Weather byWeatherDate = weatherRepository.findByWeatherDate(days);
        if (byWeatherDate == null) {
            throw new StatusCodeException(HttpStatus.NOT_FOUND, ErrorType.WEATHER_NOT_FOUND);
        } else {
            return new WeatherDTO(days, byWeatherDate.getWeatherType());
        }
    }

    public void calculateWeather() {
        weatherRepository.setBusy(true);
        try {
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
                    for (int i = 1; i <= daysToProcess; i++) {
                        for (Planet p : planets) {
                            angulePosition = calculateAngulePosition(p.getAngularVelocity(), i);
                            p.getMovements().add(new PlanetMovement(angulePosition, p));
                            points.add(GeometryUtils.polarToCartesian(p.getSunDistance(), angulePosition));
                        }
                        dayWeather = java.sql.Date.valueOf(LocalDate.now().plusDays(i));
                        points.forEach(p -> log.info(p.toString()));

                        if (rainWeatherCalculator.isRainWeather(points, i)) {
                            weathers.add(new Weather(WeatherType.RAIN, dayWeather));
                            //TODO:LLuvioso
                        } else if (droughtWeatherCalculator.isDroughtWeather(points, i)) {
                            weathers.add(new Weather(WeatherType.DROUGHT, dayWeather));
                            //TODO:Sequia
                        } else if (pressAndTempWeatherCalculator.isPressureAndTempWeather(points, i)) {
                            weathers.add(new Weather(WeatherType.PRESSURE_AND_TEMPERATURE, dayWeather));
                            //TODO:Condicion optima de Presion y Temperatura
                        } else {
                            weathers.add(new Weather(WeatherType.NORMAL, dayWeather));
                            //TODO:normal
                        }
                    }
                    planetRepository.saveAll(planets);
                    weatherRepository.saveAll(weathers);
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
        }
    }

    private long getTotalDaysToProcess(int years) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime maxTimeToProcess = today.plusYears(years);
        Duration duration = Duration.between(today, maxTimeToProcess);

        return Math.abs(duration.toDays());
    }

    private static int calculateAngulePosition(int angularVelocity, int days) {
        if (angularVelocity > 0) {
            return (days * angularVelocity) % 360;
        } else if (angularVelocity < 0) {
            int aux = angularVelocity * days;
            return (aux % 360) + 360;
        } else {
            return 0;
        }
    }
}
