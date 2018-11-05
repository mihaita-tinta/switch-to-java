package com.ing.switchtojava.carpoolingapi.rest;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.domain.Ride;
import com.ing.switchtojava.carpoolingapi.service.CarService;
import com.ing.switchtojava.carpoolingapi.service.RideService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rides/")
public class RideController {

    private static final Logger log = LoggerFactory.getLogger(RideController.class);

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @GetMapping
    public List<Ride> findAll() {
        return rideService.findAll();
    }

    @GetMapping("{id}")
    public Ride findById(@PathVariable Long id) {
        return rideService.findById(id);
    }

    @PutMapping
    public Ride save(@Valid @RequestBody Ride ride) {
        return rideService.save(ride);
    }
}
