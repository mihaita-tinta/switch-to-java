package com.ing.switchtojava.carpoolingapi.rest;

import com.ing.switchtojava.carpoolingapi.domain.Passenger;
import com.ing.switchtojava.carpoolingapi.domain.Ride;
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
    private static final Logger log = LoggerFactory.getLogger(PassengerController.class);
    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService){
        this.passengerService = passengerService;
    }

    @GetMapping
    public Iterable<Passenger> findAll() {
        return passengerService.findAll();
    }

    @GetMapping("/{id}/")
    public Passenger findById(@PathVariable Long id){
        return passengerService.findById(id);
    }

    @PutMapping
    public Passenger save(@Valid @RequestBody Passenger passenger){
        return passengerService.save(passenger);
    }

    @GetMapping("/{id}/ride-requests/")
    public List<RideRequest> listRequestsByPassenger(@PathVariable Long id){
        return passengerService.listRequestsByPassenger(id);
    }

    @PutMapping("/{id}/ride-requests/")
    public Ride joinRide(@PathVariable Long id, @Valid @RequestBody RideModel ride){
        log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        log.info(Long.toString(id));
        log.info(Long.toString(ride.getRideId()));

        return passengerService.joinRide(id, ride.getRideId());
    }

    @PutMapping("/{id}/ride-requests/{rideRequestId}")
    public RideRequest cancelRideRequestByPassenger(@PathVariable("id") Long id, @PathVariable("rideRequestId") Long rideRequestId){
        return passengerService.cancelRideRequestByPassenger(id, rideRequestId);
    }
}
