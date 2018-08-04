package com.ml.vulferbetsystem.task;

import com.ml.vulferbetsystem.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;


@Service
public class WeatherCalculatorTaskExecutor {
    private static final Logger log = LoggerFactory.getLogger(WeatherCalculatorTaskExecutor.class);


    @Autowired
    private WeatherService weatherService;

    @Scheduled(cron = "${tasks.weatherCalculator.cronExpression}")
    public void scheduledExecute() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("*** Strat ***");
        this.execute();
        stopWatch.stop();
        log.info("*** Stop in: {} ***", stopWatch.getTotalTimeSeconds());
    }


    //private methods

    private void execute() {
        weatherService.calculateWeather();
    }

}
