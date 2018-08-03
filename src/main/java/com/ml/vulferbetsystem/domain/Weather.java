package com.ml.vulferbetsystem.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "WEATHER")
public class Weather {

    @Id
    @SequenceGenerator(name="whather_generator",sequenceName="WEATHER_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="whather_generator")
    private Long id;

    @Enumerated(EnumType.STRING)
    private WeatherType weatherType;

    private Date weatherDate;


    //getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(WeatherType weatherType) {
        this.weatherType = weatherType;
    }

    public Date getWeatherDate() {
        return weatherDate;
    }

    public void setWeatherDate(Date weatherDate) {
        this.weatherDate = weatherDate;
    }
}
