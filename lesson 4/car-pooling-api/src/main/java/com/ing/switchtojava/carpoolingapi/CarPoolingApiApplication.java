package com.ing.switchtojava.carpoolingapi;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.domain.Driver;
import com.ing.switchtojava.carpoolingapi.domain.Location;
import com.ing.switchtojava.carpoolingapi.domain.Ride;
import com.ing.switchtojava.carpoolingapi.repository.CarRepository;
import com.ing.switchtojava.carpoolingapi.repository.DriverRepository;
import com.ing.switchtojava.carpoolingapi.repository.LocationRepository;
import com.ing.switchtojava.carpoolingapi.service.RideService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.ZonedDateTime;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;

@SpringBootApplication
public class CarPoolingApiApplication {
	private static final Logger log = LoggerFactory.getLogger(CarPoolingApiApplication.class);

	@Autowired
	RideService rideService;

	@Autowired
	LocationRepository locationRepository;

	@Autowired
	DriverRepository driverRepository;

	@Autowired
	CarRepository carRepository;


	@Bean
	CommandLineRunner onStartup() {
		return args -> {
			Location a = new Location();
			a.setAddress("aleea lacul morii nr. 4");
			a.setCity("Bucuresti");
			a.setZip("123-123");
			locationRepository.save(a);

			Location b = new Location();
			b.setAddress("aleea lacul morii nr. 10");
			b.setCity("Bucuresti");
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

		};
	}
	public static void main(String[] args) {
        log.debug("main - start");
		SpringApplication.run(CarPoolingApiApplication.class, args);
	}
}
