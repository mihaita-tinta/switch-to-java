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

import com.ing.switchtojava.carpoolingapi.domain.Ride;
import com.ing.switchtojava.carpoolingapi.service.RideService;

import javax.validation.Valid;

@RestController
@RequestMapping("/rides/")
public class RideController {
    private static final Logger log = LoggerFactory.getLogger(RideController.class);
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

    @PutMapping
    public Ride save(@Valid @RequestBody Ride ride) {
        return service.save(ride);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleDriverNotFound(PassengerNotFoundException e) {
        log.warn("handleDriverNotFound - " + e);
    }
}
