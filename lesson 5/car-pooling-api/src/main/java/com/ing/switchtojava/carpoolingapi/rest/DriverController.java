package com.ing.switchtojava.carpoolingapi.rest;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.domain.Driver;
import com.ing.switchtojava.carpoolingapi.repository.CarRepository;
import com.ing.switchtojava.carpoolingapi.repository.DriverRepository;
import com.ing.switchtojava.carpoolingapi.service.DriverService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/drivers/")
public class DriverController {
    private final DriverService driverService;

    DriverController(DriverService driverService ){
        this.driverService = driverService;
    }

    @GetMapping
    public Iterable<Driver> findAll(){
        return  driverService.findAll();
    }

    @GetMapping("{id}")
    public Driver findById(@PathVariable Long id) {
        return driverService.findById(id);
    }

    @RequestMapping("cars/{id}")
    public List<Car> findCarsbyDriver(@PathVariable Long id){
       return driverService.findCarsbyDriver(id);
    }


    @PutMapping
    public Driver saveDriver(@RequestBody Driver driver){
        return  driverService.saveDriver(driver);
    }

    @RequestMapping("save_car/{idDriver}")
    public Car saveCar(@PathVariable Long idDriver ,
                           @RequestBody Car car ){
        return driverService.saveCar(idDriver, car);
    }
}
