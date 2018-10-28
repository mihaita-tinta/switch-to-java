package com.ing.carpooling.repository;

import static java.time.ZonedDateTime.now;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ing.carpooling.domain.Ride;

public class RideRepositoryTest extends RepositoryIntegrationTest {
    private static final Logger log = LoggerFactory.getLogger(RideRepositoryTest.class.getName());

    PassengerRepository passengerRepository = context.getBean(PassengerRepository.class);
    CarRepository carRepository = context.getBean(CarRepository.class);
    LocationRepository locationRepository = context.getBean(LocationRepository.class);
    RideRepository rideRepository = context.getBean(RideRepository.class);

    @Test
    public void testCrud() {

        Ride ride = new Ride();
        ride.setStatus(Ride.Status.PENDING);
        ride.setCar(carRepository.findOne(3L).get());
        ride.setTo(locationRepository.findOne(3L).get());
        ride.setFrom(locationRepository.findOne(4L).get());
        ride.setPassengers(Arrays.asList(passengerRepository.findOne(5L).get()));
        ride.setWhen(now());

        Ride savedRide = rideRepository.save(ride);
        assertNotNull(savedRide.getId());


        Optional<Ride> found = rideRepository.findOne(savedRide.getId());
        found.orElseThrow(() -> new IllegalStateException("No Ride found"));

        rideRepository.findAll()
            .forEach(loc -> {
                log.info("testCrud - Ride: {}", loc);
            });
        rideRepository.delete(savedRide.getId());

        rideRepository.findOne(savedRide.getId())
            .ifPresent(location1 -> {
                throw new IllegalStateException("Ride should have been deleted");
            });
    }


    @Test
    public void testNotFound() {
        Optional<Ride> ride = rideRepository.findOne(11111111L);

        assertEquals(false, ride.isPresent());
    }

}
