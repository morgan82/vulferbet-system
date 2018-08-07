package com.ml.vulferbetsystem.componet;

import com.ml.vulferbetsystem.domain.Point;

import java.util.List;

public interface DroughtWeatherCalculator {
    boolean isDroughtWeather(List<Point> planetLocations);
}
