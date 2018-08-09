package com.ml.vulferbetsystem.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

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

    private int positionAngle;
    @Temporal(TemporalType.DATE)
    private Date positionDate;

    public PlanetMovement(int positionAngle, Planet planet, Date positionDate) {
        this.positionAngle = positionAngle;
        this.planet = planet;
        this.positionDate = positionDate;
    }

    public PlanetMovement() {
    }

    //getters and setters

    public Date getPositionDate() {
        return positionDate;
    }

    public void setPositionDate(Date positionDate) {
        this.positionDate = positionDate;
    }

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

    public int getPositionAngle() {
        return positionAngle;
    }

    public void setPositionAngle(int positionAngle) {
        this.positionAngle = positionAngle;
    }
}
