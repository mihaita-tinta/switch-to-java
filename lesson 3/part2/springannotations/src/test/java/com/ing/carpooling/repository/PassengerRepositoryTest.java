package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Car;
import com.ing.carpooling.domain.Passenger;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;

public class PassengerRepositoryTest extends RepositoryIntegrationTest {
    private static final Logger log = LoggerFactory.getLogger(PassengerRepositoryTest.class.getName());

    PassengerRepository passengerRepository = context.getBean(PassengerRepository.class);

    @Test
    public void testSaveToDatabase() {
        Passenger passenger = new Passenger();
        passenger.setFirstName("Gigel");
        passenger.setLastName("Aurel");

        Passenger saved = passengerRepository.save(passenger);
        System.out.println(passenger);
        assertNotNull(saved.getId());

        Passenger passenger2 = new Passenger();
        passenger2.setFirstName("IOnel");
        passenger2.setLastName("IOnut");
        passengerRepository.save(passenger2);
    }

    @Test
    public void findPassengerTest(){
        Optional<Passenger> p1 = passengerRepository.findOne(2L);
        if(p1.isPresent()){
//            System.out.println("aaa");
            System.out.println(p1);
        }

//        List<Passenger> passengers = passengerRepository.findAll();
//        for(Passenger passenger: passengers){
//            System.out.println(passenger);
//        }
    }

    @Test
    public void deletePassengersTest(){
        Optional<Passenger> p1 = passengerRepository.findOne(1L);
        System.out.println(p1);
        passengerRepository.delete(1L);
        Optional<Passenger> p2 = passengerRepository.findOne(1L);
        System.out.println(p2);
    }
}