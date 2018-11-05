package com.ing.switchtojava.carpoolingapi.service;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.domain.Driver;
import com.ing.switchtojava.carpoolingapi.exception.CarNotFoundException;
import com.ing.switchtojava.carpoolingapi.exception.DriverNotFoundException;
import com.ing.switchtojava.carpoolingapi.repository.CarRepository;
import com.ing.switchtojava.carpoolingapi.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository repository;

    public CarService(CarRepository repository) {
        this.repository = repository;
    }


    public List<Car> findAll() {
        return repository.findAll();
    }

    public Car findById(Long id) {
        return repository.findById(id).orElseThrow(CarNotFoundException::new);
    }

    public Car save(Car car) {
        return repository.save(car);
    }
}
