package com.ml.vulferbetsystem.weather;

public enum WeatherType {

    RAIN("Lluvia"),
    DROUGHT("Sequia"),
    PRESSURE("condicion optima de Presion"),
    TEMPERATURE("condicion optima de Temperatura");

    private String value;

    WeatherType(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
