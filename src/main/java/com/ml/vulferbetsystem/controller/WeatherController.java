package com.ml.vulferbetsystem.controller;

import com.ml.vulferbetsystem.dto.IndexDTO;
import com.ml.vulferbetsystem.dto.WeatherAndPlanetDTO;
import com.ml.vulferbetsystem.dto.WeatherDTO;
import com.ml.vulferbetsystem.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
    private static final Logger log = LoggerFactory.getLogger(WeatherController.class);
    @Value("${root.index.url}")
    private String[] urls;
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/")
    public IndexDTO index() {
        return new IndexDTO(urls);
    }

    @GetMapping("/hello-world")
    public String sayHello() {
        return "Hello World";
    }

    @GetMapping(value = "/weather", produces = MediaType.APPLICATION_JSON_VALUE)
    public WeatherDTO getWeatherByDay(@RequestParam(value = "day") int day) {
        return weatherService.getWeatherByDay(day);
    }

    @GetMapping(value = "/weather-full", produces = MediaType.APPLICATION_JSON_VALUE)
    public WeatherAndPlanetDTO getWeatherFullByDay(@RequestParam(value = "day") int day) {
        return weatherService.getWeatherAndPlanetByDay(day);
    }

}
