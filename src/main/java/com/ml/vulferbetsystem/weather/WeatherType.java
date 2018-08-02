package com.ml.vulferbetsystem.weather;

import com.fasterxml.jackson.annotation.JsonValue;

public enum WeatherType {

    RAIN("Lluvia"),
    DROUGHT("Sequia"),
    PRESSURE("condicion optima de Presion"),
    TEMPERATURE("condicion optima de Temperatura");

    private String value;

    WeatherType(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return this.value;
    }

}
