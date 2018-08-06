package com.ml.vulferbetsystem.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum WeatherType {

    RAIN("Lluvia"),
    MAX_RAIN("Lluvia, dia de mayor lluvia en el periodo"),
    DROUGHT("Sequia"),
    PRESSURE_AND_TEMPERATURE("Condicion optima de Presion y temperatura"),
    NORMAL("Normal");

    private String value;

    WeatherType(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return this.value;
    }

}
