package com.ing.switchtojava.carpoolingapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class CarPoolingApiApplication {
    private static final Logger log = LoggerFactory.getLogger(CarPoolingApiApplication.class);


    public static void main(String[] args) {
        log.debug("main - start");
        SpringApplication.run(CarPoolingApiApplication.class, args);
    }
}
