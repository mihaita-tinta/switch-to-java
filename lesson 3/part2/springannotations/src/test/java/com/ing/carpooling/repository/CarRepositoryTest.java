package com.ing.carpooling.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ing.carpooling.domain.Car;

public class CarRepositoryTest extends RepositoryIntegrationTest {
    private static final Logger log = LoggerFactory.getLogger(CarRepositoryTest.class.getName());

    CarRepository repository = context.getBean(CarRepository.class);

    @Test
    public void testCrud() {
        Car car = new Car();
        car.setNumber("ASD");
        car.setSeats(3);
        Car saved = repository.save(car);
        assertNotNull(saved.getId());

        Optional<Car> found = repository.findOne(saved.getId());
        found.orElseThrow(() -> new IllegalStateException("No car found"));

        repository.findAll()
            .forEach(loc -> {
                log.info("testCrud - Car: {}", loc);
            });
        repository.delete(saved.getId());

        repository.findOne(saved.getId())
            .ifPresent(location1 -> {
                throw new IllegalStateException("Car should have been deleted");
            });
    }


    @Test
    public void testNotFound() {
        Optional<Car> car = repository.findOne(11111111L);

        assertEquals(false, car.isPresent());
    }

}
