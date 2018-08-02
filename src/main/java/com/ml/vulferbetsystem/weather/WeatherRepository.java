package com.ml.vulferbetsystem.weather;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    WeatherRepository findByWeatherDate(Date date);
}
