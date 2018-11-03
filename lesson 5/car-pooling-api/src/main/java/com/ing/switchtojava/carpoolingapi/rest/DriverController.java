package com.ing.switchtojava.carpoolingapi.rest;

import com.ing.switchtojava.carpoolingapi.domain.Driver;
import com.ing.switchtojava.carpoolingapi.repository.DriverRepository;
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
    private final DriverRepository repository;

    public DriverController(DriverRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Driver> findAll() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Driver findById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new DriverNotFoundException());
    }


    @PostMapping
    public Driver save(@Valid @RequestBody Driver driver) {
        return repository.save(driver);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleDriverNotFound(DriverNotFoundException e) {
        log.warn("handleDriverNotFound - " + e);
    }
}
