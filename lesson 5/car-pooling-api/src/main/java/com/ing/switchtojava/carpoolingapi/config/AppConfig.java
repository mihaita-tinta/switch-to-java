package com.ing.switchtojava.carpoolingapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Configuration
public class AppConfig {

    @Bean
    public SseEmitter getSseEmiter() {
        return new SseEmitter();
    }
}
