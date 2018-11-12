package com.ing.switchtojava.carpoolingapi.rest;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.repository.CarRepository;
import com.ing.switchtojava.carpoolingapi.rest.notFoundExp.CarNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cars/")
public class CarController {

    private static final Logger log = LoggerFactory.getLogger(CarController.class);

    private final CarRepository repository;

    public CarController(CarRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Iterable<Car> findAll() { return repository.findAll(); }

    @GetMapping("{id}")
    public Car findById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CarNotFoundException());
    }

    @PutMapping
    public Car save(@Valid @RequestBody Car car) {
        return repository.save(car);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleCarNotFound(CarNotFoundException e) {
        log.warn("handleCarNotFound - " + e);
    }

}
