package com.ing.switchtojava.carpoolingapi.service;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.domain.Driver;
import com.ing.switchtojava.carpoolingapi.domain.Location;
import com.ing.switchtojava.carpoolingapi.domain.Ride;
import com.ing.switchtojava.carpoolingapi.repository.CarRepository;
import com.ing.switchtojava.carpoolingapi.repository.DriverRepository;
import com.ing.switchtojava.carpoolingapi.repository.LocationRepository;
import com.ing.switchtojava.carpoolingapi.repository.RideRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

import static java.util.Arrays.asList;

@Service
@ConditionalOnProperty(value = "carpooling.database.sampleData.enable", matchIfMissing = true)
public class StartupService implements CommandLineRunner {
    @Autowired
    RideService rideService;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    RideRequestRepository rideRequestRepository;

    @Override
    public void run(String... args) {

        Location a = new Location();
        a.setLatitude(44.4513003);
        a.setLongitude(26.0415585);
        a.setAddress("Crangasi");
        a.setCity("Bucuresti");
        a.setZip("123-123");
        a.setState("B");
        locationRepository.save(a);

        Location b = new Location();
        b.setLatitude(44.4581351);
        b.setLongitude(26.0542499);
        b.setAddress("Arcul de Triumf");
        b.setCity("Bucuresti");
        b.setState("B");
        b.setZip("123-125");
        locationRepository.save(b);

        Car car = new Car();
        car.setNumber("IL33ASD");
        car.setSeats(5);
        carRepository.save(car);

        Car car2 = new Car();
        car2.setNumber("IL44WEB");
        car2.setSeats(5);
        carRepository.save(car2);

        Driver driver = new Driver();
        driver.setFirstName("Mih");
        driver.setCars(asList(car, car2));
        driverRepository.save(driver);

        Ride ride = new Ride();
        ride.setCar(car);
        ride.setFrom(a);
        ride.setTo(b);
        ride.setWhen(ZonedDateTime.now());

        rideService.save(ride);
    }

}
