package com.ml.vulferbetsystem.service;

import com.ml.vulferbetsystem.error.ErrorType;
import com.ml.vulferbetsystem.error.StatusCodeException;
import com.ml.vulferbetsystem.domain.Weather;
import com.ml.vulferbetsystem.repositories.weather.WeatherRepositoryWrapper;
import com.ml.vulferbetsystem.dto.WeatherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class WeatherService {

    @Autowired
    private WeatherRepositoryWrapper repository;

    public WeatherDTO getWeatherByDay(int days) {

        Weather byWeatherDate = repository.findByWeatherDate(days);
        if (byWeatherDate == null) {
            throw new StatusCodeException(HttpStatus.NOT_FOUND, ErrorType.WEATHER_NOT_FOUND);
        } else {
            return new WeatherDTO(days, byWeatherDate.getWeatherType());
        }
    }
}
