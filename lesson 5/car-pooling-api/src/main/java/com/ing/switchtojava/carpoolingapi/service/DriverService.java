package com.ing.switchtojava.carpoolingapi.service;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.domain.Driver;
import com.ing.switchtojava.carpoolingapi.exception.DriverNotFoundException;
import com.ing.switchtojava.carpoolingapi.repository.CarRepository;
import com.ing.switchtojava.carpoolingapi.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final CarRepository carRepository;

    public DriverService(DriverRepository driverRepository, CarRepository carRepository) {
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
    }


    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    public Driver findById(Long id) {
        return driverRepository.findById(id).orElseThrow(DriverNotFoundException::new);
    }

    public Driver save(Driver driver) {
        return driverRepository.save(driver);
    }

    public List<Car> saveCars(Long id, List<Car> cars) {
        Driver driver = driverRepository.findById(id).orElseThrow(DriverNotFoundException::new);
        cars.forEach(car -> {
            Car driverCar = carRepository.save(car);
            driver.getCars().add(driverCar);
        });
        driverRepository.save(driver);
        return driver.getCars();
    }

    public List<Car> getCars(Long id) {
        Driver driver = driverRepository.findById(id).orElseThrow(DriverNotFoundException::new);
        return driver.getCars();
    }
}
