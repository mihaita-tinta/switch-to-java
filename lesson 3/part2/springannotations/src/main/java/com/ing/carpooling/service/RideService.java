package com.ing.carpooling.service;

import com.ing.carpooling.domain.*;
import com.ing.carpooling.repository.CarRepository;
import com.ing.carpooling.repository.RideRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    public Ride save(Driver driver, Car car, Location from, Location to, ZonedDateTime when) {

        return null;
    }

    public RideRequest join(Passenger passenger, Ride ride) {

        return null;
    }

    public RideRequest approve(RideRequest rideRequest) {

        return rideRequest;
    }


    public RideRequest reject(RideRequest rideRequest) {

        return rideRequest;
    }


    public Ride start(Driver driver, Ride ride) {

        return ride;
    }

    public Ride finish(Driver driver, Ride ride) {

        return ride;
    }
}
