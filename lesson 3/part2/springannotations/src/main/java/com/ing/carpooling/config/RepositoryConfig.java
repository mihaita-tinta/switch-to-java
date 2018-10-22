package com.ing.carpooling.config;

import com.ing.carpooling.domain.Passenger;
import com.ing.carpooling.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class RepositoryConfig {
    private static final Logger log = LoggerFactory.getLogger(RepositoryConfig.class);

    @Bean
    public LocationRepository locationRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new LocationRepository(namedParameterJdbcTemplate);
    }

    // TODO here you need to add your repository beans and modify if needed the declarations below

    @Bean
    public DriverRepository driverRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        return new DriverRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public PassengerRepository passengerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        return new PassengerRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public CarRepository carRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new CarRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public RideRepository rideRepository() {
        return new RideRepository();
    }
}
