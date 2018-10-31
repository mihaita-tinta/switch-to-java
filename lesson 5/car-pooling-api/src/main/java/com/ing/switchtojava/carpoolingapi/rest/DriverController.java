package com.ing.switchtojava.carpoolingapi.rest;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.domain.Driver;
import com.ing.switchtojava.carpoolingapi.repository.DriverRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers/")
public class DriverController {
    private final DriverRepository driverRepository;

    DriverController(DriverRepository repository){
        this.driverRepository = repository;
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

    @RequestMapping("save_cars/{idDriver}")
    public  Driver saveCar(@PathVariable Long idDriver ,
                           @RequestBody CarRequest carRequest ){
        return null;
    }

    //requestbody, cand tb sa iau date din request
    //requestparam pentru ? (pentru request pe filtre




}
