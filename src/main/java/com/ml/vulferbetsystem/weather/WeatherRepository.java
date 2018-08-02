package com.ml.vulferbetsystem.weather;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    @Query(value = "SELECT * FROM WEATHER WHERE WEATHER_DATE = ((SELECT min(WEATHER_DATE) FROM WEATHER )+?1)", nativeQuery = true)
    Weather findByWeatherDate(int days);
}
