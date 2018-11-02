package com.ing.switchtojava.carpoolingapi.service;

import com.ing.switchtojava.carpoolingapi.CarPoolingApiApplication;
import com.ing.switchtojava.carpoolingapi.domain.*;
import com.ing.switchtojava.carpoolingapi.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

import static java.util.Arrays.asList;

@Service
@ConditionalOnProperty(value = "carpooling.database.sampleData.enable", matchIfMissing = true)
public class StartupService implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(CarPoolingApiApplication.class);

    @Autowired
    RideService rideService;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public void run(String... args) {

        log.info("Create user sandu");
        User admin = new User();
        admin.setUsername("sandu");
        admin.setPassword(encoder.encode("123123"));
        userRepository.save(admin);
        UserRole roleAdmin = new UserRole();
        roleAdmin.setRole("ADMIN");
        roleAdmin.setUser(admin);
        userRoleRepository.save(roleAdmin);
        UserRole roleUser = new UserRole();
        roleUser.setRole("USER");
        roleUser.setUser(admin);
        userRoleRepository.save(roleUser);

        log.info("Create user test");
        User testUser = new User();
        testUser.setUsername("test");
        testUser.setPassword(encoder.encode("test"));
        userRepository.save(testUser);
        UserRole roleTest = new UserRole();
        roleTest.setRole("USER");
        roleTest.setUser(testUser);
        userRoleRepository.save(roleTest);

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
        carRepository.save(car);

        Driver driver = new Driver();
        driver.setFirstName("Mih");
        driver.setCars(asList(car));
        driverRepository.save(driver);

        Ride ride = new Ride();
        ride.setCar(car);
        ride.setFrom(a);
        ride.setTo(b);
        ride.setWhen(ZonedDateTime.now());

        rideService.save(ride);
    }

}
