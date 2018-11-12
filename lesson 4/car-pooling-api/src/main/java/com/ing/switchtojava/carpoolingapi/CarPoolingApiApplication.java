package com.ing.switchtojava.carpoolingapi;

import com.ing.switchtojava.carpoolingapi.domain.Location;
import com.ing.switchtojava.carpoolingapi.domain.Ride;
import com.ing.switchtojava.carpoolingapi.repository.LocationRepository;
import com.ing.switchtojava.carpoolingapi.service.RideService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Locale;

@SpringBootApplication
public class CarPoolingApiApplication {

    private static final Logger log = LoggerFactory.getLogger(CarPoolingApiApplication.class);

    @Autowired
	RideService rideService;

    @Autowired
	LocationRepository locationRepository;

    @Bean
	CommandLineRunner onStartup() {
    	return args -> {
			Location a = new Location();
			a.setAddress("aaa");
			locationRepository.save(a);

			Location b = new Location();
			b.setAddress("bbb");
			locationRepository.save(b);

			Ride ride = new Ride();
			rideService.save(ride);
		};
	}

	public static void main(String[] args) {
	    log.debug("main - start");
		SpringApplication.run(CarPoolingApiApplication.class, args);
	}
}
