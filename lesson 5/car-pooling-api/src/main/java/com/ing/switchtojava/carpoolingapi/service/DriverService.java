package com.ing.switchtojava.carpoolingapi.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.domain.Driver;
import com.ing.switchtojava.carpoolingapi.repository.DriverRepository;
import com.ing.switchtojava.carpoolingapi.rest.DriverNotFoundException;

@Service
public class DriverService {
    static Logger LOGGER = LoggerFactory.getLogger(DriverService.class);
    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    public Driver findById(Long id) {
        LOGGER.debug("Searching for driver by id: " + id);
        return driverRepository.findById(id).orElseThrow(DriverNotFoundException::new);
    }

    public Driver save(Driver driver) {

        Driver asd = new Driver();
        asd.setFirstName("asd");
        asd.setLastName("asdas");
        return driverRepository.save(asd);
    }

    public List<Car> saveCars(Long driverId, List<Car> cars) {
        Driver driver = findById(driverId);

        LOGGER.debug("Driver found: " + driver.toString());

        driver.getCars().stream().forEach(car -> LOGGER.debug("Driver's cars: " + car.toString()));

        for (Car car : cars) {
            LOGGER.debug("Adding car: " + car.toString());

            driver.getCars().add(car);
        }
        driverRepository.save(driver);

        return driver.getCars();
    }


}
