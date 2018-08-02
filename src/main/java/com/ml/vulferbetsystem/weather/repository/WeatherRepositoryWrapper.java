package com.ml.vulferbetsystem.weather.repository;

import com.ml.vulferbetsystem.config.ErrorType;
import com.ml.vulferbetsystem.config.StatusCodeException;
import com.ml.vulferbetsystem.weather.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class WeatherRepositoryWrapper {

    @Autowired
    private WeatherRepository repository;

    private volatile boolean busy = false;

    public Weather findByWeatherDate(int days) {

        if (!busy) {
            return this.repository.findByWeatherDate(days);
        } else {
            throw new StatusCodeException(HttpStatus.UNPROCESSABLE_ENTITY, ErrorType.PROCESSING_WEATHER);
        }
    }



}
