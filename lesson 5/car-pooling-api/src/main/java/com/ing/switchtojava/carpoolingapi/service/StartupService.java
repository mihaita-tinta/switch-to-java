package com.ing.switchtojava.carpoolingapi.service;

import com.ing.switchtojava.carpoolingapi.domain.*;
import com.ing.switchtojava.carpoolingapi.repository.*;
import com.ing.switchtojava.carpoolingapi.rest.CarPositionController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

@Service
@ConditionalOnProperty(value = "carpooling.database.sampleData.enable", matchIfMissing = true)
public class StartupService implements CommandLineRunner {
    @Autowired
    RideService rideService;

    @Autowired
    LocationService locationService;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    PassengerRepository passengerRepository;

    @Autowired
    RideRequestRepository rideRequestRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CarPositionRepository carPositionRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public void run(String... args) {

        Location a = new Location();
        a.setLatitude(44.4513003);
        a.setLongitude(26.0415585);
        a.setAddress("Crangasi");
        a.setCity("Bucuresti");
        a.setZip("123-123");
        a.setState("B");
        locationService.save(a);

        Location b = new Location();
        b.setLatitude(44.4581351);
        b.setLongitude(26.0542499);
        b.setAddress("Arcul de Triumf");
        b.setCity("Bucuresti");
        b.setState("B");
        b.setZip("123-125");
        locationService.save(b);

        Car car = new Car();
        car.setNumber("IL33ASD");
        carRepository.save(car);

        Driver driver = new Driver();
        driver.setFirstName("Mih");
        driver.setCars(asList(car));
        driverRepository.save(driver);

        Passenger p1 = new Passenger();
        p1.setFirstName("Andreea");
        p1.setLastName("Marin");
        passengerRepository.save(p1);

        Passenger p2 = new Passenger();
        p2.setFirstName("Mihaela");
        p2.setLastName("Radulescu");
        passengerRepository.save(p2);

        Ride ride = new Ride();
        ride.setCar(car);
        ride.setFrom(a);
        ride.setTo(b);
        ride.setWhen(ZonedDateTime.now());
        List<Passenger> passengerList = new ArrayList<Passenger>();
        passengerList.add(p1);
        passengerList.add(p2);
        ride.setPassengers(passengerList);
        rideService.save(ride);

        RideRequest rideRequest = new RideRequest();
        rideRequest.setPassenger(p1);
        rideRequest.setStatus(RideRequest.Status.ACCEPTED);
        rideRequest.setRide(ride);
        rideRequestRepository.save(rideRequest);

        User user = new User();
        user.setUsername("john");
        user.setPassword(passwordEncoder.encode("snow"));
        user.setRole("ADMIN");
        userRepository.save(user);

        User user2 = new User();
        user2.setUsername("user");
        user2.setPassword(passwordEncoder.encode("password"));
        user2.setRole("USER");
        userRepository.save(user2);


    }

}
