package com.ml.vulferbetsystem.repositories.weather;

import com.ml.vulferbetsystem.domain.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface WeatherRepository extends JpaRepository<Weather, Long> {

    @Query(value = "SELECT * FROM WEATHER WHERE WEATHER_DATE = ((SELECT min(WEATHER_DATE) FROM WEATHER )+(?1-1))", nativeQuery = true)
    Weather findByWeatherDate(int days);
}
