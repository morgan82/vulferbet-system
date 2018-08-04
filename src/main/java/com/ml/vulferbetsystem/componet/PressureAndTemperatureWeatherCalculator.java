package com.ml.vulferbetsystem.componet;

import com.ml.vulferbetsystem.domain.Point;

import java.util.List;

public interface PressureAndTemperatureWeatherCalculator {
    boolean isPressureAndTempWeather(List<Point> planetLocations, int days);
}
