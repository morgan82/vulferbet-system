package com.ml.vulferbetsystem.cron;

import com.ml.vulferbetsystem.domain.ConfigParam;
import com.ml.vulferbetsystem.domain.ConfigParamConstants;
import com.ml.vulferbetsystem.repositories.ConfigParamRepository;
import com.ml.vulferbetsystem.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
public class ProcessingWeatherTask {
    private static final Logger log = LoggerFactory.getLogger(ProcessingWeatherTask.class);
    @Autowired
    private WeatherService weatherService;

    @Autowired
    private ConfigParamRepository configParamRepository;

    public void processingWeather() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            log.info("*** Start task processing weather***");
            setProcessing(true);
            weatherService.processWeather();
        } catch (Exception e) {
            log.error("Error in task processing weather", e);
        } finally {
            stopWatch.stop();
            setProcessing(false);
            log.info("*** Stop task processing weather processing in {}***", stopWatch.getTotalTimeSeconds());
        }

    }

    public void setProcessing(Boolean flag) {
        ConfigParam isProcessing = configParamRepository.findByName(
                ConfigParamConstants.IS_PROCESS_WEATHER.name());

        isProcessing.setValue(flag.toString());
        configParamRepository.save(isProcessing);
    }
}
