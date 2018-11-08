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

import com.ing.switchtojava.carpoolingapi.domain.Passenger;
import com.ing.switchtojava.carpoolingapi.domain.RideRequest;
import com.ing.switchtojava.carpoolingapi.service.PassengerService;

import javax.validation.Valid;

@RestController
@RequestMapping("/passengers/")
public class PassengerController {
    private static final Logger log = LoggerFactory.getLogger(PassengerController.class);
    private final PassengerService service;

    public PassengerController(PassengerService service) {
        this.service = service;
    }

    @GetMapping
    public List<Passenger> findAll() {
        return service.findAll();
    }

    @GetMapping("{id}")
    public Passenger findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping
    public Passenger save(@Valid @RequestBody Passenger passenger) {
        return service.save(passenger);
    }

    @GetMapping("{id}/ride-requests")
    public List<RideRequest> findRequestsByPassenger(@PathVariable Long id) {
        return service.findPassengerRides(id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleDriverNotFound(PassengerNotFoundException e) {
        log.warn("handleDriverNotFound - " + e);
    }
}
