package com.ing.switchtojava.carpoolingapi.rest;

import com.ing.switchtojava.carpoolingapi.domain.*;
import com.ing.switchtojava.carpoolingapi.repository.PassengerRepository;
import com.ing.switchtojava.carpoolingapi.repository.RideRepository;
import com.ing.switchtojava.carpoolingapi.repository.RideRequestRepository;
import com.ing.switchtojava.carpoolingapi.rest.notFoundExp.PassengerNotFoundException;
import com.ing.switchtojava.carpoolingapi.rest.notFoundExp.RideNotFoundException;
import com.ing.switchtojava.carpoolingapi.rest.notFoundExp.RideRequestNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/passengers/")
public class PassengerController {

    private static final Logger log = LoggerFactory.getLogger(PassengerController.class);

    private final PassengerRepository repository;

    @Autowired
    RideRequestRepository rideRequestRepository;

    @Autowired
    RideRepository rideRepository;

    public PassengerController(PassengerRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Iterable<Passenger> findAll() { return repository.findAll(); }

    @GetMapping("{id}")
    public Passenger findById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PassengerNotFoundException());
    }

    @GetMapping("{id}/ride-requests")
    public RideRequest findByIdGetRideRequests(@PathVariable Long id) {
        Passenger passenger = repository.findById(id).orElseThrow(() -> new PassengerNotFoundException());
        return rideRequestRepository.findByPassenger(passenger).orElseThrow(() -> new RideRequestNotFoundException());

//        List<RideRequest> rideRequestList = rideRequestRepository.findAll();
//        for (RideRequest rideRequest : rideRequestList)
//            if (rideRequest.getPassenger().getId() == id)
//                return rideRequest;
//        return null;
    }

    @PutMapping
    public Passenger save(@Valid @RequestBody Passenger passenger) {
        return repository.save(passenger);
    }

    @PutMapping("{id}/ride-requests")
    public Car saveRideRequest(@PathVariable Long id, @RequestBody Ride ride) {
        Passenger passenger = repository.findById(id).orElseThrow(() -> new PassengerNotFoundException());
        Ride r = rideRepository.findById(ride.getId()).orElseThrow(() -> new RideNotFoundException());
        RideRequest rideRequest = new RideRequest();
        rideRequest.setRide(r);
        rideRequest.setPassenger(passenger);
        rideRequest.setStatus(RideRequest.Status.PENDING);
        rideRequestRepository.save(rideRequest);
        return rideRequest.getRide().getCar();
    }

    @PatchMapping(value = "{id}/ride-requests/{rideRequestId}/status",consumes = {"text/plain", "application/json"})
    public RideRequest updateRideRequestStatus(@PathVariable(name="id") Long id, @PathVariable(name="rideRequestId") Long rideRequestId, @RequestBody String status) {
        Passenger passenger = repository.findById(id).orElseThrow(() -> new PassengerNotFoundException());
        RideRequest rideRequest = rideRequestRepository.findById(rideRequestId).orElseThrow(() -> new RideRequestNotFoundException());
        RideRequest.Status rideStatus = null;
        try {
            rideStatus = RideRequest.Status.valueOf(status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rideStatus != null)
            rideRequest.setStatus(rideStatus);
        return rideRequestRepository.save(rideRequest);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handlePassengerNotFound(PassengerNotFoundException e) { log.warn("handlePassengerNotFound - " + e); }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleRideRequestNotFound(RideRequestNotFoundException e) { log.warn("handleRideRequestNotFound - " + e); }

}
