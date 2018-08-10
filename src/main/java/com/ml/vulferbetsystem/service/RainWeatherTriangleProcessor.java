package com.ml.vulferbetsystem.service;

import com.ml.vulferbetsystem.domain.Point;
import com.ml.vulferbetsystem.utils.GeometryUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RainWeatherTriangleProcessor implements RainWeatherProcessor {
    @Override
    public boolean isRainWeather(List<Point> planetLocations) {
        Point p1 = planetLocations.get(0);
        Point p2 = planetLocations.get(1);
        Point p3 = planetLocations.get(2);

        return GeometryUtils.isTriangle(p1, p2, p3) && GeometryUtils.originBelongToTriangle(p1, p2, p3);
    }
}
