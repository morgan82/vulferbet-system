package com.ml.vulferbetsystem.unit;

import com.ml.vulferbetsystem.controller.WeatherController;
import com.ml.vulferbetsystem.service.WeatherService;
import net.minidev.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(WeatherController.class)
public class WeatherControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WeatherService weatherService;

    @Test
    public void weatherControllerGetWeatherWhitoutParamDay() throws Exception {
        this.mockMvc.perform(get("/weather"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void weatherControllerGetWeatherOK() throws Exception {
        this.mockMvc.perform(get("/weather?day=1"))
                .andDo(print())
                .andExpect(status().isOk());
    }


}
