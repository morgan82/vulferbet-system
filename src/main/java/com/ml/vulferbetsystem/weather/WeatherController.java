package com.ml.vulferbetsystem.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/hello-world")
    public String sayHello() {
        return "Hello World";
    }

    @GetMapping(value = "/weather", produces = MediaType.APPLICATION_JSON_VALUE)
    public WeatherDTO getWeatherByDay(@RequestParam(value = "day") int day) {

        return weatherService.getWeatherByDay(day);
    }
}
