package com.ml.vulferbetsystem.weather;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @GetMapping("/hello-world")
    public String sayHello() {
        return "Hello World";
    }

    @GetMapping(value = "/weather", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getWeatherByDay(@RequestParam(value = "day") String day) {

        return "TODO: clima del sistema vulferbet con dia: " + day;
    }
}
