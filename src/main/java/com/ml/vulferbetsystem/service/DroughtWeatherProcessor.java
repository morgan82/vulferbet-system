package com.ml.vulferbetsystem.service;

import com.ml.vulferbetsystem.domain.Point;

import java.util.List;

public interface DroughtWeatherProcessor {
    boolean isDroughtWeather(List<Point> planetLocations);
}
