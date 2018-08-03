package com.ml.vulferbetsystem.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PLANET_MOVEMENT")
public class PlanetMovement {

    @Id
    @SequenceGenerator(name = "planet_movement_generator", sequenceName = "PLANET_MOVEMENT_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "planet_movement_generator")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PLANET_ID", nullable = false)
    private Planet planet;

    private int angulePosition;

    public PlanetMovement(int angulePosition, Planet planet) {
        this.angulePosition = angulePosition;
        this.planet = planet;
    }

    public PlanetMovement() {
    }

    //getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Planet getPlanet() {
        return planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

    public int getAngulePosition() {
        return angulePosition;
    }

    public void setAngulePosition(int angulePosition) {
        this.angulePosition = angulePosition;
    }
}
