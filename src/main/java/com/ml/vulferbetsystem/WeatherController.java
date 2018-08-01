package com.ml.vulferbetsystem;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WeatherController {

    @GetMapping("/hello-world")
    @ResponseBody
    public String sayHello() {
        return  "Hello World";
    }

    @GetMapping(path = "/weather", produces = "application/json")
    @ResponseBody
    public String getWeatherByDay(@RequestParam(value = "day") String day) {
        return  "TODO: clima del sistema vulferbet con dia: "+day;
    }
}
