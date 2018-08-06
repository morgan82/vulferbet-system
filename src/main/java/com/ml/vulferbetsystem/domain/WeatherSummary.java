package com.ml.vulferbetsystem.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class WeatherSummary {
    private static final Logger log = LoggerFactory.getLogger(WeatherSummary.class);

    private static int rainWatherCount = 0;
    private static int droughtWatherCount = 0;
    private static int pressAndTempWatherCount = 0;
    private static int normalWatherCount = 0;
    private static Date maxRainWeatherDay;


    public static void addWeather(Weather w) {
        switch (w.getWeatherType()) {
            case MAX_RAIN:
                rainWatherCount++;
                maxRainWeatherDay = w.getWeatherDate();
                break;
            case RAIN:
                rainWatherCount++;
                break;
            case DROUGHT:
                droughtWatherCount++;
                break;
            case PRESSURE_AND_TEMPERATURE:
                pressAndTempWatherCount++;
                break;
            default:
                normalWatherCount++;
        }
    }

    public static void clearValues() {
        droughtWatherCount = 0;
        rainWatherCount = 0;
        pressAndTempWatherCount = 0;
        normalWatherCount = 0;
        maxRainWeatherDay = null;
    }

    public static void printSummary() {
        log.info("");
    }
}
