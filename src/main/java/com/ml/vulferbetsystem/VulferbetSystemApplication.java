package com.ml.vulferbetsystem;

import com.ml.vulferbetsystem.cron.ProcessingWeatherTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class VulferbetSystemApplication {
    private static final Logger log = LoggerFactory.getLogger(VulferbetSystemApplication.class);
    @Autowired
    private ProcessingWeatherTask task;
    @PostConstruct
    public void onStartup() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            log.info("+++ Start in Start Application +++");
            task.processingWeather();
        } catch (Exception e) {
            log.error("Ocurrio un error al levantar la aplicacion", e);
        } finally {
            stopWatch.stop();
            log.info("+++ Stop in: {} seconds in Start Application +++", stopWatch.getTotalTimeSeconds());
        }

    }
    public static void main(String[] args) {
        SpringApplication.run(VulferbetSystemApplication.class, args);
    }
}
