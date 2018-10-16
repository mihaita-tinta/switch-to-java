package com.ing.ayo.config;

import com.ing.ayo.Application;
import com.ing.ayo.repository.CarRepository;
import com.ing.ayo.repository.RideRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
    private static final Logger log = LoggerFactory.getLogger(RepositoryConfig.class);

    @Bean
    public CarRepository carRepository() {
        CarRepository carRepository = new CarRepository();
        log.info("carRepository - creating a new bean carRepository: {}", carRepository);
        return carRepository;
    }

    @Bean
    public RideRepository rideRepository() {
        RideRepository rideRepository = new RideRepository();
        log.info("rideRepository - creating a new bean rideRepository: {}", rideRepository);
        return rideRepository;
    }
}
