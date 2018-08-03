package com.ml.vulferbetsystem.service;

import com.ml.vulferbetsystem.domain.Point;

import java.util.List;

public interface PressureAndTemperatureWeatherCalculator {
    boolean isPressureAndTempWeather(List<Point> planetLocations, int days);
}
