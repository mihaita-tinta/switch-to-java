package com.ing.switchtojava.carpoolingapi.rest;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars/")
public class CarController {
    private static final Logger log = LoggerFactory.getLogger(DriverController.class);
    private final CarRepository carRepository;

    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @PostMapping
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @GetMapping
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @GetMapping("{id}")
    public Car findOne(@PathVariable Long id) {
        return carRepository.findById(id).orElseThrow(() -> new CarNotFoundException());
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        this.carRepository.deleteById(id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleCarNotFound(CarNotFoundException e) {
        log.warn("Car not found", e);
    }
}