package com.ing.carpooling.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ing.carpooling.domain.Driver;

public class DriverRepositoryTest extends RepositoryIntegrationTest {
    private static final Logger log = LoggerFactory.getLogger(DriverRepositoryTest.class.getName());

    DriverRepository driverRepository = context.getBean(DriverRepository.class);
    CarRepository carRepository = context.getBean(CarRepository.class);

    @Test
    public void testCrud() {

        Driver driver = new Driver();
        driver.setFirstName("FName");
        driver.setLastName("Last");
        driver.setCars(Arrays.asList(carRepository.findOne(3L).get()));
        Driver saved = driverRepository.save(driver);
        assertNotNull(saved.getId());

//        Optional<Driver> found = driverRepository.findOne(saved.getId());
//        found.orElseThrow(() -> new IllegalStateException("No Driver found"));

        driverRepository.findAll()
            .forEach(drivers -> {
                log.info("testCrud - Driver: {}", drivers);
            });
        driverRepository.delete(saved.getId());

        driverRepository.findOne(saved.getId())
            .ifPresent(location1 -> {
                throw new IllegalStateException("Driver should have been deleted");
            });
    }


    @Test
    public void testNotFound() {
        Optional<Driver> driver = driverRepository.findOne(11111111L);

        assertEquals(false, driver.isPresent());
    }

}
