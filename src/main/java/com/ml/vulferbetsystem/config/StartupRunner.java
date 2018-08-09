package com.ml.vulferbetsystem.config;

import com.ml.vulferbetsystem.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
public class StartupRunner implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(StartupRunner.class);
    @Autowired
    private WeatherService weatherService;

    @Override
    public void run(ApplicationArguments args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            log.info("****** Your application started with option names : {}", args.getOptionNames());
            log.info("*** Strat in runner***");
            weatherService.calculateWeather();
        } catch (Exception e) {
            log.error("Ocurrio un error en el runner");
        } finally {
            stopWatch.stop();
            log.info("*** Finished in runner in {} seconds***", stopWatch.getTotalTimeSeconds());
        }
    }
}
