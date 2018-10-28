package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Car;
import com.ing.carpooling.domain.Driver;
import com.ing.carpooling.domain.Location;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LocationRepositoryTest extends RepositoryIntegrationTest {
    private static final Logger log = LoggerFactory.getLogger(LocationRepositoryTest.class.getName());

    LocationRepository repository = context.getBean(LocationRepository.class);
    //CarRepository carRepository = context.getBean(CarRepository.class);
    //DriverRepository driverRepository = context.getBean(DriverRepository.class);

//    @Test
//    public void test1() {
//        Car car = new Car();
//        car.setNumber("IL11OIS");
//        car.setId(1l);
//        car.setSeats(1);
//        car.setDriverId(1l);
//        Car saved = carRepository.save(car);
//        System.out.println(saved);
//        assertNotNull(saved.getId());
//
//    }
//        Driver driver =new Driver();
//        driver.setId(1l);
//        driver.setFirstName("adi");
//        driver.setLastName("thewonder");
//        driver.setCars(Arrays.asList(car));
//        Driver ss = driverRepository.save(driver);



    @Test
    public void testCrud() {
        Location location = new Location();
        location.setLatitude(11.11);
        location.setLongitude(12.1321);
        location.setAddress("S-Park, A1, Poligrafiei 123");
        location.setCity("Bucuresti");
        location.setState("Bucuresti");
        location.setZip("123-123");
        Location saved = repository.save(location);
        assertNotNull(saved.getId());

//        Optional<Location> found = repository.findOne(saved.getId());
//        found.orElseThrow(() -> new IllegalStateException("No location found"));
//
//        repository.findAll()
//                .forEach(loc -> {
//                    log.info("testCrud - location: {}", loc);
//                });
//        repository.delete(saved.getId());
//
//        repository.findOne(saved.getId())
//                .ifPresent(location1 ->  {throw new IllegalStateException("Location should have been deleted");});
    }


    @Test
    public void testNotFound() {
        Optional<Location> location = repository.findOne(11111111L);

        assertEquals(false, location.isPresent());
    }

}
