package com.ml.vulferbetsystem.repositories;

import com.ml.vulferbetsystem.domain.PlanetMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanetMovementRepository extends JpaRepository<PlanetMovement, Long> {

    @Query(value = "SELECT * FROM PLANET_MOVEMENT WHERE POSITION_DATE = ((SELECT min(POSITION_DATE) FROM PLANET_MOVEMENT )+(?1-1))", nativeQuery = true)
    List<PlanetMovement> findAllByPositionDate(int days);
}
