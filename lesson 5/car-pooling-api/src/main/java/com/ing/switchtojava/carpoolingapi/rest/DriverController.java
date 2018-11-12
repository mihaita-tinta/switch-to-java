package com.ing.switchtojava.carpoolingapi.rest;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.domain.Driver;
import com.ing.switchtojava.carpoolingapi.repository.DriverRepository;
import com.ing.switchtojava.carpoolingapi.rest.notFoundExp.DriverNotFoundException;
import com.ing.switchtojava.carpoolingapi.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/drivers/")
public class DriverController {

    private static final Logger log = LoggerFactory.getLogger(DriverController.class);
    private final DriverRepository repository;

    @Autowired
    CarService carService;

    public DriverController(DriverRepository repository) { this.repository = repository; }

    @GetMapping
    public Iterable<Driver> findAll() { return repository.findAll(); }

    @GetMapping("{id}")
    public Driver findById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException());
    }

    @GetMapping("{id}/cars")
    public List<Car> findByIdGetCars(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException()).getCars();
    }

    @PutMapping
    public Driver save(@Valid @RequestBody Driver driver) {
        return repository.save(driver);
    }

    @PutMapping("{id}/cars")
    public List<Car> saveCars(@PathVariable Long id, @RequestBody Car car) {
        Driver d = repository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException());

        carService.save(car);
        d.getCars().add(car);
        d = repository.save(d);
        return d.getCars();

    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleDriverNotFound(DriverNotFoundException e) {
        log.warn("handleDriverNotFound - " + e);
    }

}
