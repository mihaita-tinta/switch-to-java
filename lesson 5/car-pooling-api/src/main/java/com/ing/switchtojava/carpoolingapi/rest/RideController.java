package com.ing.switchtojava.carpoolingapi.rest;

import com.ing.switchtojava.carpoolingapi.domain.Ride;
import com.ing.switchtojava.carpoolingapi.service.RideService;
import com.ing.switchtojava.carpoolingapi.service.TrackingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.validation.Valid;
import javax.xml.bind.JAXBException;
import java.io.IOException;

@RestController
@RequestMapping("/rides/")
public class RideController {
    private static final Logger log = LoggerFactory.getLogger(PassengerController.class);
    private final RideService rideService;

//    @Autowired
//    TrackingService trackingService;
    private final TrackingService trackingService;

    public RideController(RideService rideService, TrackingService trackingService){
        this.trackingService=trackingService;
        this.rideService=rideService;
    }


    @GetMapping
    public Iterable<Ride> findAll() {
        return rideService.findAll();
    }

    @GetMapping("/{id}")
    public Ride findById(@PathVariable Long id){
        return rideService.findOne(id);
    }

    @PutMapping
    public Ride save(@Valid @RequestBody Ride ride) {
        return rideService.save(ride);
    }


    @GetMapping("/{id}/track")
    public SseEmitter getPosition(@PathVariable Long id) throws JAXBException, IOException {
        return trackingService.trackPosition(id);
    }
}
