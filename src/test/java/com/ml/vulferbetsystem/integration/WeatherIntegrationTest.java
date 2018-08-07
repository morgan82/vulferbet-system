package com.ml.vulferbetsystem.integration;

import com.ml.vulferbetsystem.domain.WeatherType;
import com.ml.vulferbetsystem.dto.WeatherDTO;
import com.ml.vulferbetsystem.error.ErrorMessage;
import com.ml.vulferbetsystem.error.ErrorType;
import com.ml.vulferbetsystem.service.WeatherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.EnumSet;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeatherIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private WeatherService weatherService;

    @Test
    public void weatherControllerHelloWordOk() {
        ResponseEntity<String> result = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/hello-world", String.class);
        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void weatherControllerGetWeatherOK() {
        ResponseEntity<WeatherDTO> result = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/weather?day=123", WeatherDTO.class);
        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result.getHeaders().getContentType().isCompatibleWith(MediaType.APPLICATION_JSON)).isTrue();
        then(result.getBody().getDay()).isEqualTo(123);
        then(EnumSet.allOf(WeatherType.class).contains(result.getBody().getWeather())).isTrue();

    }

    @Test
    public void weatherControllerGetWeatherNoFound() {
        ResponseEntity<ErrorMessage> result = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/weather?day=-123", ErrorMessage.class);
        then(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(result.getBody().getMessage()).isEqualTo(ErrorType.WEATHER_NOT_FOUND);
    }
}
