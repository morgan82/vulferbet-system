package com.ml.vulferbetsystem.repositories.weather;

import com.ml.vulferbetsystem.domain.Weather;
import com.ml.vulferbetsystem.error.ErrorType;
import com.ml.vulferbetsystem.error.StatusCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Weather> saveAll(List<Weather> weathers) {
        return this.repository.saveAll(weathers);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    //getter and setter
    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }
}
