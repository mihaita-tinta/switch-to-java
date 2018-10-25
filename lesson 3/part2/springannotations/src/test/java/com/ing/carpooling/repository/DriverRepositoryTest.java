package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Car;
import com.ing.carpooling.domain.Driver;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class DriverRepositoryTest extends RepositoryIntegrationTest {
    public static final Logger log= LoggerFactory.getLogger(DriverRepository.class);

    DriverRepository repository = context.getBean(DriverRepository.class);

    @Test
    public void TestCrud()
    {
        Driver driver = new Driver();
        driver.setFirstName("dan");
        driver.setLastName("James");

        Car car = new Car();
        car.setNumber("now");
        car.setSeats(2);

        List listOfCars = new ArrayList();
        listOfCars.add(car);
        driver.setCars(listOfCars);

        repository.save(driver);
    }

}
