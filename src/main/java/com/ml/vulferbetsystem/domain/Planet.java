package com.ml.vulferbetsystem.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PLANET")
public class Planet {

    @Id
    @SequenceGenerator(name = "planet_generator", sequenceName = "PLANET_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "planet_generator")
    private Long id;

    private String name;

    private double sunDistance;

    private int angularVelocity;

    private int lastPosition;

    @OneToMany(mappedBy = "planet", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlanetMovement> movements = new ArrayList<>();

    //getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSunDistance() {
        return sunDistance;
    }

    public void setSunDistance(int sunDistance) {
        this.sunDistance = sunDistance;
    }

    public int getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(int angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    public int getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

    public List<PlanetMovement> getMovements() {
        return movements;
    }

    public void setMovements(List<PlanetMovement> movements) {
        this.movements = movements;
    }

    public int calculateMovement(int day) {
        return Math.abs(day * angularVelocity) % 360;
    }
}
