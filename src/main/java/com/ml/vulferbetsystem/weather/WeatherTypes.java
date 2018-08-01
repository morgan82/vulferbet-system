package com.ml.vulferbetsystem.weather;

public enum WeatherTypes {

    RAIN("Lluvia"),
    DROUGHT("Sequia"),
    PRESSURE("condicion optima de Presion"),
    TEMPERATURE("condicion optima de Temperatura");

    private String value;

    WeatherTypes(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
