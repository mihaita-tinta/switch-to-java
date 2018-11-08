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
import com.ing.switchtojava.carpoolingapi.service.CarService;

import javax.validation.Valid;

@RestController
@RequestMapping("/cars/")
public class CarController {
    private static final Logger log = LoggerFactory.getLogger(CarController.class);
    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @GetMapping
    public List<Car> findAll() {
        return service.findAll();
    }

    @GetMapping("{id}")
    public Car findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping
    public Car save(@Valid @RequestBody Car car) {
        return service.save(car);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleLocationNotFound(CarNotFoundException e) {
        log.warn("handleLocationNotFound - " + e);
    }
}
