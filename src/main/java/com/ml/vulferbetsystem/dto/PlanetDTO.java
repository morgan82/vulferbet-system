package com.ml.vulferbetsystem.dto;

import com.ml.vulferbetsystem.domain.Point;

public class PlanetDTO {
    private String name;
    private Point position;

    public PlanetDTO() {
    }

    public PlanetDTO(String name, Point position) {
        this.name = name;
        this.position = position;
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
