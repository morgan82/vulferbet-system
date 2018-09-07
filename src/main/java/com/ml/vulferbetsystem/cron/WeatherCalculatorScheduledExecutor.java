package com.ml.vulferbetsystem.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;


@Service
public class WeatherCalculatorScheduledExecutor {
    private static final Logger log = LoggerFactory.getLogger(WeatherCalculatorScheduledExecutor.class);
    @Autowired
    private ProcessingWeatherTask task;

    @Scheduled(cron = "${tasks.weatherCalculator.cronExpression}")
    public void scheduledExecute() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            log.info("--- Start in scheduler---");
            task.processingWeather();
        } catch (Exception e) {
            log.error("Ocurrio un error al ejecutar el job in schedule", e);
        } finally {
            stopWatch.stop();
            log.info("--- Stop in: {} seconds in scheduler---", stopWatch.getTotalTimeSeconds());
        }
    }

}
