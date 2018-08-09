package com.ml.vulferbetsystem.repositories;

import com.ml.vulferbetsystem.domain.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends JpaRepository<Planet,Long> {
    @Query(value = "SELECT * FROM WEATHER WHERE WEATHER_DATE = ((SELECT min(WEATHER_DATE) FROM WEATHER )+(?1-1))", nativeQuery = true)
    Planet findByWeatherDate(int days);

}
