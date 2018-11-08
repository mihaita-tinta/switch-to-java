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

import com.ing.switchtojava.carpoolingapi.domain.Location;
import com.ing.switchtojava.carpoolingapi.repository.LocationRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("/locations/")
public class LocationController {
    private static final Logger log = LoggerFactory.getLogger(LocationController.class);
    private final LocationRepository repository;

    public LocationController(LocationRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Location> findAll() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Location findById(@PathVariable Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new LocationNotFoundException());
    }

    @PutMapping
    public Location save(@Valid @RequestBody Location location) {
        return repository.save(location);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleLocationNotFound(LocationNotFoundException e) {
        log.warn("handleLocationNotFound - " + e);
    }
}
