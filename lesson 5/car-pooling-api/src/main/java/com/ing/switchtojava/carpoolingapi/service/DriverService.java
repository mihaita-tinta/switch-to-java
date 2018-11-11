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

    private DriverRepository driverRepository;
    private CarRepository carRepository;

    DriverService(DriverRepository driverRepository, CarRepository carRepository){
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
    }

    public Iterable<Driver> findAll()
    {
        return  driverRepository.findAll();
    }

    public Driver findById(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException());
    }

    public List<Car> findCarsbyDriver(Long id){
        return driverRepository.findById(id).get().getCars();
    }

    public Driver saveDriver(Driver driver){
        return  driverRepository.save(driver);
    }

    public Car saveCar(Long idDriver, Car car){
        Driver driver = this.findById(idDriver);
        //car = carRepository.save(car);
        driver.getCars().add(car);
        driverRepository.save(driver);
        return driver.getCars().get(1);
    }

}
