package com.ing.switchtojava.carpoolingapi.service;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.repository.CarRepository;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    private final CarRepository repository;

    public CarService(CarRepository repository){
        this.repository = repository;
    }

    public Iterable<Car> findAll(){
        return repository.findAll();
    }
}
