package com.ml.vulferbetsystem.dto;

import com.ml.vulferbetsystem.domain.WeatherType;

import java.util.List;

public class WeatherAndPlanetDTO {
    private int day;
    private WeatherType weather;
    private List<PlanetDTO> planets;
    private String easyFormat;

    public WeatherAndPlanetDTO() {
    }

    public WeatherAndPlanetDTO(int day, WeatherType weather) {
        this.day = day;
        this.weather = weather;
    }

    public WeatherAndPlanetDTO(int day, WeatherType weather, List<PlanetDTO> planets) {
        this.day = day;
        this.weather = weather;
        this.planets = planets;
        this.easyFormat = "(" + planets.get(0).getPosition().getX() + "," + planets.get(0).getPosition().getY() + ")"
                + "(" + planets.get(1).getPosition().getX() + "," + planets.get(1).getPosition().getY() + ")"
                + "(" + planets.get(2).getPosition().getX() + "," + planets.get(2).getPosition().getY() + ")";
    }

    //getters and setters

    public String geteasyFormat() {
        return easyFormat;
    }

    public int getDay() {
        return day;
    }

    public WeatherType getWeather() {
        return weather;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setWeather(WeatherType weather) {
        this.weather = weather;
    }

    public List<PlanetDTO> getPlanets() {
        return planets;
    }

    public void setPlanets(List<PlanetDTO> planets) {
        this.planets = planets;
    }
}
