package com.ing.switchtojava.carpoolingapi.rest;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.domain.Driver;
import com.ing.switchtojava.carpoolingapi.repository.CarRepository;
import com.ing.switchtojava.carpoolingapi.repository.DriverRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/drivers/")
public class DriverController {
    private final DriverRepository driverRepository;
    private final CarRepository carRepository;

    DriverController(DriverRepository driverRepository , CarRepository carRepository){
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
    }

    @GetMapping
    public Iterable<Driver> findAll(){
        return  driverRepository.findAll();
    }

    @GetMapping("{id}")
    public Driver findById(@PathVariable Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException());
    }

    @RequestMapping("cars/{id}")
    public List<Car> findCarsbyDriver(@PathVariable Long id){
       return driverRepository.findById(id).get().getCars();
    }

    @RequestMapping("save_car/{idDriver}")
    public Car saveCar(@PathVariable Long idDriver ,
                           @RequestBody CarRequest carRequest ){

        List<Car> cars =  new ArrayList<Car>();
        Driver driver = this.findById(idDriver);
        cars = driver.getCars();
       // cars.add(carRequest.car);
        driver.setCars(cars);

        driver =  driverRepository.save(driver);
        return driver.getCars().get(1);
        /*Car car = carRepository.save(carRequest);
        cars = driver.getCars();
        cars.add(carRequest);
        driver.setCars(cars);
        return driver.getCars().get(1);*/
    }

    //requestbody, cand tb sa iau date din request
    //requestparam pentru ? (pentru request pe filtre




}
