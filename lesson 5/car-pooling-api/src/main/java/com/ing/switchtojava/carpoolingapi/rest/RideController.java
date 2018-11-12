package com.ing.switchtojava.carpoolingapi.rest;

import com.ing.switchtojava.carpoolingapi.domain.Ride;
import com.ing.switchtojava.carpoolingapi.service.RideService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rides")
public class RideController {

    public static final Logger log = LoggerFactory.getLogger(RideController.class);
    private final RideService service;

    public RideController(RideService service) {
        this.service = service;
    }

    @GetMapping
    public List<Ride> findAll() {
        return service.findAll();
    }

    @GetMapping("{id}")
    public Ride findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Ride save(@RequestBody Ride ride) {
        return service.save(ride);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleRideNotFoundException(RideNotFoundException e) {
        log.warn("Handle Rider not found exception", e);
    }
}
