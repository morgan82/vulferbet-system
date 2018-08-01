package com.ml.vulferbetsystem.api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class WeatherController {

    @RequestMapping(value = "hello-world")
    public String sayHello() {
        return "Hello World";
    }

    @RequestMapping(value = "weather", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getWeatherByDay(@RequestParam(value = "day") String day) {

        return "TODO: clima del sistema vulferbet con dia: " + day;
    }
}
