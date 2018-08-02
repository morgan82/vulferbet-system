package com.ml.vulferbetsystem.task;

import com.ml.vulferbetsystem.config.ConfigParam;
import com.ml.vulferbetsystem.config.ConfigParamConstants;
import com.ml.vulferbetsystem.config.ConfigParamRepository;
import com.ml.vulferbetsystem.planet.Planet;
import com.ml.vulferbetsystem.planet.PlanetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.List;

@Service
public class WeatherCalculatorTaskExecutor {
    private static final Logger log = LoggerFactory.getLogger(WeatherCalculatorTaskExecutor.class);

    @Autowired
    private PlanetRepository planetRepository;
    @Autowired
    private ConfigParamRepository configParamRepository;

    @Scheduled(cron = "${tasks.weatherCalculator.cronExpression}")
    public void scheduledExecute() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("*** Strat ***");
        this.execute();
        stopWatch.stop();
        log.info("*** Stop in: {} ***",stopWatch.getTotalTimeMillis());
    }

    private void execute() throws InterruptedException {
        Thread.sleep(1000);
        log.info("procesando...");
        ConfigParam fullProcessing = configParamRepository.findByName(ConfigParamConstants.FULL_WEATHER_PROCESSING.name());
        if (fullProcessing != null) {
            if (Boolean.valueOf(fullProcessing.getValue())) {
                List<Planet> planets = planetRepository.findAll();
                planets.forEach(planet -> log.info(planet.getName()));
            }

        } else {
            log.error("Falta Parametro de Inicializacion para calcular el clima");
        }


    }
}
