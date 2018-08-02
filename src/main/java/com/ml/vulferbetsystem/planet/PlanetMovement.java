package com.ml.vulferbetsystem.planet;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PLANET_ID", nullable = false)
    private Planet planet;

    private int positionDegree;

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

    public int getPositionDegree() {
        return positionDegree;
    }

    public void setPositionDegree(int positionDegree) {
        this.positionDegree = positionDegree;
    }
}
