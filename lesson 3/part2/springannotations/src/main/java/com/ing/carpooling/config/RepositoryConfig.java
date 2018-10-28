package com.ing.carpooling.config;

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
    public CarRepository carRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new CarRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public DriverRepository driverRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, CarRepository carRepository) {
        return new DriverRepository(namedParameterJdbcTemplate, carRepository);
    }

    @Bean
    public PassengerRepository passengerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        return  new PassengerRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public RideRequestRepository rideRequestRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, RideRepository rideRepository,
                                                PassengerRepository passengerRepository) {
            return new RideRequestRepository(namedParameterJdbcTemplate, rideRepository, passengerRepository);
    }

    @Bean
    public RideRepository rideRepository(NamedParameterJdbcTemplate namedJdbcTemplate, PassengerRepository passengerRepository,
                                         LocationRepository locationRepository, CarRepository carRepository) {
        return new RideRepository(namedJdbcTemplate, passengerRepository, locationRepository, carRepository);
    }
}
