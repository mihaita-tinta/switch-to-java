package com.ing.carpooling.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.ing.carpooling.repository.CarRepository;
import com.ing.carpooling.repository.LocationRepository;
import com.ing.carpooling.repository.RideRepository;

@Configuration
public class RepositoryConfig {
    private static final Logger log = LoggerFactory.getLogger(RepositoryConfig.class);

    @Bean
    public LocationRepository locationRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new LocationRepository(namedParameterJdbcTemplate);
    }

    // TODO here you need to add your repository beans and modify if needed the declarations below

    @Bean
    public CarRepository carRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new CarRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public RideRepository rideRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, CarRepository carRepository, LocationRepository locationRepository) {
        return new RideRepository(namedParameterJdbcTemplate, carRepository, locationRepository);
    }
}
