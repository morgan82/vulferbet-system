package com.ml.vulferbetsystem.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeatherIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void weatherControllerHelloWordOk(){
        ResponseEntity<String> result = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/hello-world", String.class);
        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void weatherControllerGetWeatherOK(){
        ResponseEntity<String> result = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/weather?day=123", String.class);
        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
