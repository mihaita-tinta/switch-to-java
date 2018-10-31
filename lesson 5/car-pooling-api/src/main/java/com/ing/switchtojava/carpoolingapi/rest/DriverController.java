package com.ing.switchtojava.carpoolingapi.rest;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.domain.Driver;
import com.ing.switchtojava.carpoolingapi.repository.DriverRepository;
import com.ing.switchtojava.carpoolingapi.rest.model.CarListRequest;
import com.ing.switchtojava.carpoolingapi.service.DriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/drivers/")
public class DriverController {
    private static final Logger log = LoggerFactory.getLogger(LocationController.class);
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
    public Driver save(@Valid @RequestBody Driver driver) {
        return driverService.save(driver);
    }

    @PutMapping("{id}/cars")
    public List<Car> addCars(@PathVariable Long id, @Valid @RequestBody CarListRequest cars) {
        return driverService.saveCars(id, cars.getCarList());
    }
}
