package com.ing.switchtojava.carpoolingapi.service;

import com.ing.switchtojava.carpoolingapi.domain.*;
import com.ing.switchtojava.carpoolingapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public void run(String... args) {
        Role role1 = new Role();
        role1.setRole(Role.Roles.ADMIN.toString());
        Role role2 = new Role();
        role1.setRole("USER");

        roleRepository.save(role1);
        roleRepository.save(role2);


        User user = new User();
        user.setPassword(encoder.encode("admin"));
        user.setUsername("admin");
        Set set = new HashSet();
        set.add(role1);

        user.setRoles(set);

        userRepository.save(user);
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
