package com.ing.switchtojava.carpoolingapi.rest;

import com.ing.switchtojava.carpoolingapi.domain.Ride;
import com.ing.switchtojava.carpoolingapi.repository.RideRepository;
import com.ing.switchtojava.carpoolingapi.rest.notFoundExp.RideNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rides/")
public class RideController {

    private static final Logger log = LoggerFactory.getLogger(CarController.class);

    private final RideRepository repository;

    public RideController(RideRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Iterable<Ride> findAll() { return repository.findAll(); }

    @GetMapping("{id}")
    public Ride findById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new RideNotFoundException());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleRideRequestNotFound(RideNotFoundException e) {
        log.warn("handleRideNotFound - " + e);
    }

}
