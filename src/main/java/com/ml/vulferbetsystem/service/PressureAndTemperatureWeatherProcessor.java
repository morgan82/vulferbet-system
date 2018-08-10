package com.ml.vulferbetsystem.service;

import com.ml.vulferbetsystem.domain.Point;

import java.util.List;

public interface PressureAndTemperatureWeatherProcessor {
    boolean isPressureAndTempWeather(List<Point> planetLocations);
}
