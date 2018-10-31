package com.ing.switchtojava.carpoolingapi.rest;

import com.ing.switchtojava.carpoolingapi.domain.Passenger;
import com.ing.switchtojava.carpoolingapi.domain.RideRequest;
import com.ing.switchtojava.carpoolingapi.service.PassengerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/passengers/")
public class PassengerController {
    private static final Logger log = LoggerFactory.getLogger(LocationController.class);
    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    public List<Passenger> findAll() {
        return passengerService.findAll();
    }

    @GetMapping("{id}")
    public Passenger findById(@PathVariable Long id) {
        return passengerService.findById(id);
    }

    @PutMapping
    public Passenger save(@Valid @RequestBody Passenger passenger) {
        return passengerService.save(passenger);
    }

    @GetMapping("{id}/ride-requests/")
    public List<RideRequest> findRideRiquests(@PathVariable Long id) {
        return passengerService.findRideRequests(id);
    }
}
