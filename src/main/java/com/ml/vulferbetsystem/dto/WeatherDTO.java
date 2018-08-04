package com.ml.vulferbetsystem.dto;

import com.ml.vulferbetsystem.domain.WeatherType;

import java.util.List;

public class WeatherDTO {
    private int day;
    private WeatherType weather;
    private List<PlanetDTO> planets;

    public WeatherDTO() {
    }

    public WeatherDTO(int day, WeatherType weather) {
        this.day = day;
        this.weather = weather;
    }

    public WeatherDTO(int day, WeatherType weather, List<PlanetDTO> planets) {
        this.day = day;
        this.weather = weather;
        this.planets = planets;
    }

    //getters and setters
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
