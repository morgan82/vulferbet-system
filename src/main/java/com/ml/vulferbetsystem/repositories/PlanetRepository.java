package com.ml.vulferbetsystem.repositories;

import com.ml.vulferbetsystem.domain.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetRepository extends JpaRepository<Planet,Long> {

}
