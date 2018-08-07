package com.ml.vulferbetsystem.domain;

import com.ml.vulferbetsystem.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WeatherSummary {
    private static final Logger log = LoggerFactory.getLogger(WeatherSummary.class);

    private static int rainWatherCount = 0;
    private static int droughtWatherCount = 0;
    private static List<Long> droughtDays = new ArrayList<>();
    private static int pressAndTempWatherCount = 0;
    private static List<Long> pressAndTempDays = new ArrayList<>();
    private static int normalWatherCount = 0;
    private static LocalDate maxRainWeatherDay;


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
                droughtDays.add(DateUtils.getDaysBetweenTodayAndOtherDate(w.getWeatherDate()));
                break;
            case PRESSURE_AND_TEMPERATURE:
                pressAndTempWatherCount++;
                pressAndTempDays.add(DateUtils.getDaysBetweenTodayAndOtherDate(w.getWeatherDate()));
                break;
            default:
                normalWatherCount++;
        }
    }

    public static void clearValues() {
        droughtWatherCount = 0;
        droughtDays.clear();
        rainWatherCount = 0;
        pressAndTempWatherCount = 0;
        pressAndTempDays.clear();
        normalWatherCount = 0;
        maxRainWeatherDay = null;
    }

    public static void printSummary() {
        log.info("Cantidad de dias de Sequia {} dias={}", droughtWatherCount, droughtDays.stream().map(Object::toString).collect(Collectors.joining(",")));
        log.info("Cantidad de dias de LLuvia {} con un pico maximo en el dia {} dia {} apartir de hoy", rainWatherCount,
                DateTimeFormatter.ofPattern("dd-MM-yyyy").format(maxRainWeatherDay), DateUtils.getDaysBetweenTodayAndOtherDate(maxRainWeatherDay));
        log.info("Cantidad de dias de Condiciones optimas de Presion y Temperatura {} dias={}", pressAndTempWatherCount, pressAndTempDays.stream().map(Object::toString).collect(Collectors.joining(",")));
        log.info("Cantidad de dias con clima Normal {} ", normalWatherCount);
    }
}
