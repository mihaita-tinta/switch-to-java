package com.ing.carpooling.repository;

import com.ing.carpooling.domain.*;
import com.ing.carpooling.service.HomeworkService;
import com.ing.carpooling.service.RideService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Optional;

public class AllClassesTest extends RepositoryIntegrationTest{

    private static final Logger log = LoggerFactory.getLogger(AllClassesTest.class.getName());

    CarRepository carRepository = context.getBean(CarRepository.class);
    DriverRepository driverRepository = context.getBean(DriverRepository.class, carRepository);
    LocationRepository locationRepository = context.getBean(LocationRepository.class);
    PassengerRepository passengerRepository = context.getBean(PassengerRepository.class);
    RideRepository rideRepository=context.getBean(RideRepository.class,passengerRepository,
            locationRepository,carRepository);
    RideRequestRepository rideRequestRepository = context.getBean(RideRequestRepository.class, rideRepository, passengerRepository);


    @Test
    public void testt(){

        Car car = new Car();
        car.setNumber("B101asd");
        car.setSeats(5);
        car.setDriverId(1l);

        Car car1 = new Car();
        car1.setNumber("BV221asd");
        car1.setSeats(6);
        car1.setDriverId(1l);

        Driver driver = new Driver();
        driver.setLastName("Dobre");
        driver.setFirstName("Andrei");
        driver.setCars(Arrays.asList(car,car1));
        driverRepository.save(driver);

        Passenger passenger = new Passenger();
        passenger.setFirstName("Gigel");
        passenger.setLastName("Aurel");
        passengerRepository.save(passenger);

        Passenger passenger2 = new Passenger();
        passenger2.setFirstName("Robert");
        passenger2.setLastName("Dorel");
        passengerRepository.save(passenger2);

        Location locationFrom = new Location();
        locationFrom.setLatitude(11.11);
        locationFrom.setLongitude(12.1321);
        locationFrom.setAddress("S-Park, A1, Poligrafiei 123");
        locationFrom.setCity("Bucuresti");
        locationFrom.setState("Bucuresti");
        locationFrom.setZip("123-123");
        locationRepository.save(locationFrom);

        Location locationTo = new Location();
        locationTo.setLatitude(11.11);
        locationTo.setLongitude(12.1321);
        locationTo.setAddress("S-Park, A1, Poligrafiei 300");
        locationTo.setCity("Bucuresti");
        locationTo.setState("Bucuresti");
        locationTo.setZip("333-444");
        locationRepository.save(locationTo);

        System.out.println(ZonedDateTime.now());
        System.out.println("Am ajuns la ride");
        Ride ride = new Ride();
        ride.setCar(car);
        ride.setFrom(locationFrom);
        ride.setTo(locationTo);
        ride.setStatus(Ride.Status.COMPLETED);
        ride.setWhen(ZonedDateTime.now());
        ride.setPassengers(Arrays.asList(passenger, passenger2));
        rideRepository.save(ride);

        System.out.println(ride);

        Optional<Ride> ridee = rideRepository.findOne(1L);

        System.out.println(ridee);
        RideRequest rideRequest = new RideRequest();
        rideRequest.setRide(ride);
        rideRequest.setPassenger(passenger);
        rideRequest.setStatus(RideRequest.Status.PENDING);
        System.out.println("ride request "+ rideRequest);
        rideRequestRepository.save(rideRequest);
    }



}
