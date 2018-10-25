package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Car;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CarRepositoryTest extends RepositoryIntegrationTest {
    private static final Logger log = LoggerFactory.getLogger(CarRepository.class);

    CarRepository repository = context.getBean(CarRepository.class);

    @Test
    public void testCRUD()
    {
        Car car = new Car();
        car.setSeats(5);
        car.setNumber("PX12AQL");
        Car savedCar = repository.save(car);
        assertNotNull(savedCar);

        Optional<Car> foundCar = repository.findOne(savedCar.getId());
        foundCar.orElseThrow(() -> new IllegalStateException("No car found"));

        repository.findAll().forEach(carItem -> log.info("found car with id {}", carItem.getId()));

       // repository.delete(savedCar.getId());
    }
}
