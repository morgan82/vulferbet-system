package com.ml.vulferbetsystem.config;

import com.ml.vulferbetsystem.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(StartupRunner.class);
    @Autowired
    private WeatherService weatherService;

    @Override
    public void run(ApplicationArguments args) {
        log.info("****** Your application started with option names : {}", args.getOptionNames());
        weatherService.calculateWeather();
    }
}
