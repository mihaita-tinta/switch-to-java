package com.ing.switchtojava.carpoolingapi.service;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.domain.Driver;
import com.ing.switchtojava.carpoolingapi.repository.CarRepository;
import com.ing.switchtojava.carpoolingapi.repository.DriverRepository;
import com.ing.switchtojava.carpoolingapi.rest.DriverNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final CarRepository carRepository;

    public DriverService(DriverRepository driverRepository, CarRepository carRepository) {
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
    }

    public Driver save(Driver driver) {
        Driver tempDriver = new Driver();
        if (driver.getCars().size() > 0) {
            for (Car c : driver.getCars()) {
                Car tempCar = carRepository.save(c);
                tempDriver.addCar(tempCar);
            }
        }
        tempDriver.setFirstName(driver.getFirstName());
        tempDriver.setLastName(driver.getLastName());
        return driverRepository.save(tempDriver);
    }

    public Car saveCar(Long id, Car car) throws DriverNotFoundException {
        Driver driver   = driverRepository.findById(id).orElseThrow(() -> new DriverNotFoundException());
        Car tempCar     = carRepository.save(car);
        driver.addCar(car);
        driverRepository.save(driver);
        return driver.getCars().get(driver.getCars().size()-1);
    }

    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    public Optional<Driver> findById(Long id) {
        return driverRepository.findById(id);
    }

    public List<Car> findCarsById(Long id) throws DriverNotFoundException {
        List <Car> cars = new ArrayList<>();
        Driver driver = driverRepository.findById(id).orElseThrow(() -> new DriverNotFoundException());
        if (driver.getCars().size() > 0) {
            cars = driver.getCars();
        }
        return cars;
    }

    public void deleteById(Long id) {
        driverRepository.deleteById(id);
    }
}