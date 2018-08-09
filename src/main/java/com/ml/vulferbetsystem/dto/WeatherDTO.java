package com.ml.vulferbetsystem.dto;

import com.ml.vulferbetsystem.domain.WeatherType;

public class WeatherDTO {
    private int day;
    private WeatherType weather;

    public WeatherDTO() {
    }

    public WeatherDTO(int day, WeatherType weather) {
        this.day = day;
        this.weather = weather;
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
}
