package com.ml.vulferbetsystem.task;

import com.ml.vulferbetsystem.domain.ConfigParam;
import com.ml.vulferbetsystem.domain.ConfigParamConstants;
import com.ml.vulferbetsystem.domain.Planet;
import com.ml.vulferbetsystem.domain.PlanetMovement;
import com.ml.vulferbetsystem.domain.Point;
import com.ml.vulferbetsystem.repositories.ConfigParamRepository;
import com.ml.vulferbetsystem.repositories.PlanetRepository;
import com.ml.vulferbetsystem.repositories.weather.WeatherRepositoryWrapper;
import com.ml.vulferbetsystem.utils.GeometryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherCalculatorTaskExecutor {
    private static final Logger log = LoggerFactory.getLogger(WeatherCalculatorTaskExecutor.class);

    @Autowired
    private PlanetRepository planetRepository;
    @Autowired
    private ConfigParamRepository configParamRepository;
    @Autowired
    private WeatherRepositoryWrapper weatherRepository;

    @Scheduled(cron = "${tasks.weatherCalculator.cronExpression}")
    public void scheduledExecute() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("*** Strat ***");
        this.execute();
        stopWatch.stop();
        log.info("*** Stop in: {} ***", stopWatch.getTotalTimeMillis());
    }


    //private methods

    private void execute() {
        weatherRepository.setBusy(true);
        try {
            log.info("procesando...");
            ConfigParam fullWeatherProcessing = configParamRepository.findByName(ConfigParamConstants.FULL_WEATHER_PROCESSING.name());
            if (fullWeatherProcessing != null) {
                if (Boolean.valueOf(fullWeatherProcessing.getValue())) {
                    ConfigParam periodToProcess = configParamRepository.findByName(ConfigParamConstants.PERIOD_TO_PROCESS_IN_YEARS.name());
                    long daysToProcess = getTotalDaysToProcess(Integer.parseInt(periodToProcess.getValue()));
                    log.info("Total days to process {} ", daysToProcess);
                    List<Planet> planets = planetRepository.findAll();
                    List<Point> points = new ArrayList<>();
                    int angulePosition;
                    for (int i = 1; i <= daysToProcess; i++) {
                        for (Planet p:planets) {
                            angulePosition = calculateAngulePosition(p.getAngularVelocity(), i);
                            p.getMovements().add(new PlanetMovement(angulePosition,p));
                            points.add(GeometryUtils.polarToCartesian(p.getSunDistance(), angulePosition));
                        }
                        boolean belongToStraight = GeometryUtils.isPointsBelongToStraight(points);
                        if (belongToStraight) {

                        }

                    }
                    planetRepository.saveAll(planets);
                }

            } else {
                log.error("Falta Parametro de Inicializacion para calcular el clima");
            }
        } catch (Throwable e) {
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
