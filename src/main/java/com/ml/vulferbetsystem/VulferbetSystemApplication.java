package com.ml.vulferbetsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VulferbetSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(VulferbetSystemApplication.class, args);
    }
}
