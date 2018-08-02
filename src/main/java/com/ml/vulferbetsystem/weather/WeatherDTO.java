package com.ml.vulferbetsystem.weather;

public class WeatherDTO {
    private int day;
    private WeatherType weatherTypes;

    //getters and setters
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public WeatherType getWeatherTypes() {
        return weatherTypes;
    }

    public void setWeatherTypes(WeatherType weatherTypes) {
        this.weatherTypes = weatherTypes;
    }
}
