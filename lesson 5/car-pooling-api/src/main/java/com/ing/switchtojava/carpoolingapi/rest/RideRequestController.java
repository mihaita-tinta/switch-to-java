package com.ing.switchtojava.carpoolingapi.rest;

import com.ing.switchtojava.carpoolingapi.domain.RideRequest;
import com.ing.switchtojava.carpoolingapi.repository.PassengerRepository;
import com.ing.switchtojava.carpoolingapi.repository.RideRequestRepository;
import com.ing.switchtojava.carpoolingapi.rest.notFoundExp.RideRequestNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/ride-requests/")
public class RideRequestController {

    private static final Logger log = LoggerFactory.getLogger(RideRequestController.class);

    private final RideRequestRepository repository;

    @Autowired
    PassengerRepository passengerRepository;

    public RideRequestController(RideRequestRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Iterable<RideRequest> findAll() { return repository.findAll(); }

    @GetMapping("{id}")
    public RideRequest findById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RideRequestNotFoundException());
    }

    @PutMapping
    public RideRequest save(@Valid @RequestBody RideRequest rideRequest) {
        return repository.save(rideRequest);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleRideRequestNotFound(RideRequestNotFoundException e) {
        log.warn("handleRideRequestNotFound - " + e);
    }

}
