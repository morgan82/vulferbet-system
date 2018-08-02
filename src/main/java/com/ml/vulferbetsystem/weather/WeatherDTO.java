package com.ml.vulferbetsystem.weather;

public class WeatherDTO {
    private int day;
    private WeatherType weatherTypes;

    public WeatherDTO(int day, WeatherType weatherTypes) {
        this.day = day;
        this.weatherTypes = weatherTypes;
    }

    //getters and setters
    public int getDay() {
        return day;
    }

    public WeatherType getWeatherTypes() {
        return weatherTypes;
    }
}
