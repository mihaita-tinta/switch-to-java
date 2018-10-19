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
        log.info("save - you need to implement this");
        // TODO 2 create a ride based on what was received and save it into the database

        return null;
    }

    public RideRequest join(Passenger passenger, Ride ride) {
        log.info("join - you need to implement this");
        // TODO 3 add a RideRequst for the ride and that passenger
        return null;
    }

    public RideRequest approve(RideRequest rideRequest) {
        log.info("approve - you need to implement this");
        // TODO 4 RideRequest status needs to be moved to ACCEPTED and the passenger added to the ride
        return rideRequest;
    }


    public RideRequest reject(RideRequest rideRequest) {
        log.info("reject - you need to implement this");
        // TODO 5 RideRequest status needs to be moved to REJECTED
        return rideRequest;
    }


    public Ride start(Ride ride) {
        log.info("start - you need to implement this");
        // TODO 6 Ride status needs to be moved to IN_PROGRESS
        return ride;
    }

    public Ride finish(Ride ride) {
        log.info("finish - you need to implement this");
        // TODO 7 Ride status needs to be moved to COMPLETED
        return ride;
    }
}
