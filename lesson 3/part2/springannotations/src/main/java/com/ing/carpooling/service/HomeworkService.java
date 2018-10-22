package com.ing.carpooling.service;


import com.ing.carpooling.domain.*;
import com.ing.carpooling.repository.CarRepository;
import com.ing.carpooling.repository.DriverRepository;
import com.ing.carpooling.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.util.Arrays;

@Service
public class HomeworkService {
    private static final Logger log = LoggerFactory.getLogger(HomeworkService.class);

    private final RideService rideService;
    private final LocationRepository locationRepository;
    private final DriverRepository driverRepository;
    private final CarRepository carRepository;

    public HomeworkService(RideService rideService, LocationRepository locationRepository,
                           DriverRepository driverRepository, CarRepository carRepository) {
        this.rideService = rideService;
        this.locationRepository = locationRepository;
        this.carRepository = carRepository;
        this.driverRepository = driverRepository;
    }

    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct - you need to add: \n" +
                "     - a driver with one car\n" +
                "     - a ride from a to b\n" +
                "     - 2 passengers, 1 of them have the RideRequest accepted and one rejected\n" +
                "     - the ride is started and then finished");
        Location from = createFrom();
        Location to = createTo();

        Driver caesar = getDriverWithCar();

        Ride ride = rideService.save(caesar, caesar.getCars().get(0), from, to, ZonedDateTime.now().plusDays(1));

        Passenger john = getPassengerJohn();
        RideRequest requestForJohn = rideService.join(john, ride);

        Passenger alexa = getPassengerAlexa();
        RideRequest requestForAlexa = rideService.join(alexa, ride);

        rideService.approve(requestForAlexa);
        rideService.reject(requestForJohn);

        rideService.start(ride);

        rideService.finish(ride);


    }

    private Passenger getPassengerJohn() {
        Passenger passenger = new Passenger();
        passenger.setFirstName("John");
        passenger.setLastName("JohnJ");
        return passenger;
    }

    private Passenger getPassengerAlexa() {
        Passenger passenger = new Passenger();
        passenger.setFirstName("Alexa");
        passenger.setLastName("AlexaJ");
        return passenger;
    }


    private Driver getDriverWithCar() {
        Car car = new Car();
        car.setNumber("IL11OIS");
        car.setSeats(4);

        Driver driver = new Driver();
        driver.setFirstName("NeeD");
        driver.setLastName("Sir");
        driver.setCars(Arrays.asList(car));

        if (driver.getId() == null || car.getId() == null) {
            driver =  driverRepository.save(driver);
            car.setDriverId(driver.getId());
            carRepository.save(car);
            log.info("getDriverWithCar - you need to create a car and a driver. You also need to build the relation between the 2 tables");
        }
        return driver;
    }

    private Location createFrom() {
        log.info("createFrom - the from location was created for you to have a starting point");
        Location location = new Location();
        location.setLatitude(11.11);
        location.setLongitude(12.1321);
        location.setAddress("S-Park, A1, Poligrafiei 123");
        location.setCity("Bucuresti");
        location.setState("Bucuresti");
        location.setZip("123-123");
        return locationRepository.save(location);
    }

    private Location createTo() {
        log.info("createTo - the to location was created for you to have a destination");
        Location location = new Location();
        location.setLatitude(11.11);
        location.setLongitude(12.1321);
        location.setAddress("S-Park, A1, Poligrafiei 123");
        location.setCity("Bucuresti");
        location.setState("Bucuresti");
        location.setZip("123-123");
        return locationRepository.save(location);
    }


}
