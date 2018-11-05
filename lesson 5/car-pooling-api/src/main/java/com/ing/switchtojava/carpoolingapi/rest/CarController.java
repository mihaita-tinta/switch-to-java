package com.ing.switchtojava.carpoolingapi.rest;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cars/")
public class CarController {

    private static final Logger log = LoggerFactory.getLogger(CarController.class);

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<Car> findAll() {
        return carService.findAll();
    }

    @GetMapping("{id}")
    public Car findById(@PathVariable Long id) {
        return carService.findById(id);
    }

    @PutMapping
    public Car save(@Valid @RequestBody Car car) {
        return carService.save(car);
    }
}
