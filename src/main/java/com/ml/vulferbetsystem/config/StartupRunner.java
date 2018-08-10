package com.ml.vulferbetsystem.config;

import com.ml.vulferbetsystem.cron.ProcessingWeatherTask;
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
    private ProcessingWeatherTask task;

    @Override
    public void run(ApplicationArguments args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            log.info("+++ Strat in runner +++");
            task.processingWeather();
        } catch (Exception e) {
            log.error("Ocurrio un error en el runner", e);
        } finally {
            stopWatch.stop();
            log.info("+++ Finished in runner in {} seconds +++", stopWatch.getTotalTimeSeconds());
        }
    }

}
