package com.ing.switchtojava.carpoolingapi.rest;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.domain.Driver;
import com.ing.switchtojava.carpoolingapi.repository.DriverRepository;
import com.ing.switchtojava.carpoolingapi.service.DriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/drivers/")
public class DriverController {
    private static final Logger log = LoggerFactory.getLogger(LocationController.class);

    private final DriverService driverService;

    public DriverController(DriverService driverService){
        this.driverService = driverService;
    }

    @GetMapping
    public Iterable<Driver> findAll() {
        return driverService.findAll();
    }

    @GetMapping("/{id}/")
    public Driver findById(@PathVariable Long id){
        return driverService.findById(id);
    }

    @GetMapping("/{id}/cars")
    public List<Car> findCarsByDriverId(@PathVariable Long id){
        return driverService.findCarsByDriverId(id);
            //    repository.findById(id).get().getCars();
    }


    @PutMapping
    public Driver save(@Valid @RequestBody Driver driver){
        return driverService.save(driver);

    }

    @PutMapping("/{id}/cars")
    public List<Car> saveCarForADriver(@PathVariable Long id, @Valid @RequestBody List<Car> car){
        log.info("#########################################");
        return driverService.saveCarForADriver(id, car);
    }

//        driver.setCars(Arrays.asList(car));
//        log.info("AM setat lista de masini");
//        repository.save(driver);
//        log.info("AM salvat driverul");
//        return repository.findById(id).get().getCars().get(0);
//
//        Driver driver = repository.findById(id).orElseThrow(DriverNotFoundException::new);
//        cars.forEach(car -> driver.getCars().add(car));
//        repository.save(driver);
//        return driver.getCars();



}
