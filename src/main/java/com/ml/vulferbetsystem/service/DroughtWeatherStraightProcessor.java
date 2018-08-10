package com.ml.vulferbetsystem.service;

import com.ml.vulferbetsystem.domain.Point;
import com.ml.vulferbetsystem.utils.GeometryUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DroughtWeatherStraightProcessor implements DroughtWeatherProcessor {
    @Override
    public boolean isDroughtWeather(List<Point> planetLocations) {
        boolean belongToStraight = GeometryUtils.isBelongToStraight(planetLocations);
        boolean straightPassForOrigin = GeometryUtils.isStraightPassForOrigin(planetLocations.get(0), planetLocations.get(1));

        return belongToStraight && straightPassForOrigin;
    }
}
