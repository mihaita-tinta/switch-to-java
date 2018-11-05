package com.ing.switchtojava.carpoolingapi.service;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.domain.Driver;
import com.ing.switchtojava.carpoolingapi.repository.CarRepository;
import com.ing.switchtojava.carpoolingapi.repository.DriverRepository;
import com.ing.switchtojava.carpoolingapi.rest.DriverNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {
    private final DriverRepository repository;
    private final CarRepository carRepository;

    public DriverService(DriverRepository repository, CarRepository carRepository){
        this.repository=repository;
        this.carRepository = carRepository;
    }

    public Driver save(Driver driver){
        return repository.save(driver);
    }

    public Iterable<Driver> findAll(){
        return repository.findAll();
    }

    public Driver findById(Long id){
        return repository.findById(id)
                .orElseThrow(()-> new DriverNotFoundException());
    }

    public List<Car> findCarsByDriverId(Long id){
        Driver driver = repository.findById(id)
                .orElseThrow(()-> new DriverNotFoundException());
        return driver.getCars();
    }

    public List<Car> saveCarForADriver(Long id, List<Car> car){
        Driver driver = repository.findById(id)
                .orElseThrow(()-> new DriverNotFoundException());

        car.forEach(e->{
            //carRepository.save(e);
            driver.getCars().add(e);

        });

        repository.save(driver);
        System.out.println(driver);
        return driver.getCars();
    }





}
