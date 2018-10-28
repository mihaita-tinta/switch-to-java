package com.ing.carpooling.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ing.carpooling.domain.Passenger;

public class PassengerRepositoryTest extends RepositoryIntegrationTest {
    private static final Logger log = LoggerFactory.getLogger(PassengerRepositoryTest.class.getName());

    PassengerRepository repository = context.getBean(PassengerRepository.class);

    @Test
    public void testCrud() {
        Passenger passenger = new Passenger();

        passenger.setFirstName("First");
        passenger.setLastName("last");

        Passenger saved = repository.save(passenger);
        assertNotNull(saved.getId());

        Optional<Passenger> found = repository.findOne(saved.getId());
        found.orElseThrow(() -> new IllegalStateException("No Passenger found"));

        repository.findAll()
            .forEach(loc -> {
                log.info("testCrud - Passenger: {}", loc);
            });
        repository.delete(saved.getId());

        repository.findOne(saved.getId())
            .ifPresent(location1 -> {
                throw new IllegalStateException("Passenger should have been deleted");
            });
    }


    @Test
    public void testNotFound() {
        Optional<Passenger> passenger = repository.findOne(11111111L);

        assertEquals(false, passenger.isPresent());
    }

}
