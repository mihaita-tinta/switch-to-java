package com.ing.switchtojava.carpoolingapi.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.repository.CarRepository;
import com.ing.switchtojava.carpoolingapi.rest.DriverNotFoundException;

@Service
public class CarService {
    static Logger LOGGER = LoggerFactory.getLogger(CarService.class);
    private final CarRepository repository;

    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    public List<Car> findAll() {
        return repository.findAll();
    }

    public Car findById(Long id) {
        return repository.findById(id).orElseThrow(DriverNotFoundException::new);
    }

    public Car save(Car car) {
        return repository.save(car);
    }


}
