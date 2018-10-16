package com.ing.ayo.service;

import com.ing.ayo.config.RepositoryConfig;
import com.ing.ayo.domain.Car;
import com.ing.ayo.domain.Driver;
import com.ing.ayo.domain.Location;
import com.ing.ayo.domain.Ride;
import com.ing.ayo.repository.CarRepository;
import com.ing.ayo.repository.RideRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class RideService {
    private static final Logger log = LoggerFactory.getLogger(RideService.class);

    private final RideRepository rideRepository;
    private final CarRepository carRepository;


    public RideService(RideRepository rideRepository, CarRepository carRepository) {
        this.rideRepository = rideRepository;
        this.carRepository = carRepository;
        log.info("RideService - rideRepository: {}, carRepository: {}", rideRepository, carRepository);
    }

    public List<Ride> findAll(RideSearchRequest request) {


        return Collections.emptyList();
    }


    public Ride save(Car car, Location from, Location to, ZonedDateTime when) {

        return null;
    }


    public static class RideSearchRequest {

        private Location from;
        private Location to;
        private ZonedDateTime start;
        private ZonedDateTime end;
        private Driver driver;

    }
}
