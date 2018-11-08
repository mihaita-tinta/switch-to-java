package com.ing.switchtojava.carpoolingapi.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.domain.Driver;
import com.ing.switchtojava.carpoolingapi.service.DriverService;

import javax.validation.Valid;

@RestController
@RequestMapping("/drivers/")
public class DriverController {
    private static final Logger log = LoggerFactory.getLogger(DriverController.class);
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public List<Driver> findAll() {
        return driverService.findAll();
    }

    @GetMapping("{id}")
    public Driver findById(@PathVariable Long id) {
        return driverService.findById(id);
    }

    @PutMapping
    public Driver save(@Valid @RequestBody Driver Driver) {
        return driverService.save(Driver);
    }

    @PutMapping("{id}/cars")
    public List<Car> addCars(@PathVariable Long id, @Valid @RequestBody List<Car> cars) {
        return driverService.saveCars(id, cars);
    }

    @GetMapping("{id}/cars")
    public List<Car> findCarsByDriver(@PathVariable Long id) {
        return driverService.findById(id).getCars();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleDriverNotFound(DriverController e) {
        log.warn("handleDriverNotFound - " + e);
    }
}
