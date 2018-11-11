package com.ing.switchtojava.carpoolingapi.rest;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.domain.Driver;
import com.ing.switchtojava.carpoolingapi.service.DriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/drivers/")
public class DriverController {
    private static final Logger log = LoggerFactory.getLogger(DriverController.class);
    private final DriverService service;

    public DriverController(DriverService service) {
        this.service = service;
    }

    @GetMapping
    public List<Driver> findAll() {
        return service.findAll();
    }

    @GetMapping("{id}")
    public Driver findById(@PathVariable Long id) {
        return service.findById(id).orElseThrow(() -> new DriverNotFoundException());
    }
    @GetMapping("{id}/cars")
    public List<Car> getCars(@PathVariable Long id) {
        return service.findCarsById(id);
    }

    @PostMapping
    public Driver save(@Valid @RequestBody Driver driver) {
        return service.save(driver);
    }

    @PutMapping("{id}/cars")
    public Car saveCar(@PathVariable Long id, @Valid @RequestBody Car car) {
        return service.saveCar(id, car);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleDriverNotFound(DriverNotFoundException e) {
        log.warn("handleDriverNotFound - ", e);
    }
}