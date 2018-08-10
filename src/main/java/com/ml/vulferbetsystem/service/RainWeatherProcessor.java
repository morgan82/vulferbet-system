package com.ml.vulferbetsystem.service;

import com.ml.vulferbetsystem.domain.Point;

import java.util.List;

public interface RainWeatherProcessor {
    boolean isRainWeather(List<Point> planetLocations);
}
