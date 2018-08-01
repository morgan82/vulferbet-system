package com.ml.vulferbetsystem.weather;

public class WeatherDTO {
    private int day;
    private WeatherTypes weatherTypes;

    //getters and setters

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public WeatherTypes getWeatherTypes() {
        return weatherTypes;
    }

    public void setWeatherTypes(WeatherTypes weatherTypes) {
        this.weatherTypes = weatherTypes;
    }
}
