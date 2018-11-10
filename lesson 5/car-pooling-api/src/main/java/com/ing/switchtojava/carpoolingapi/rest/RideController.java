package com.ing.switchtojava.carpoolingapi.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.domain.Ride;
import com.ing.switchtojava.carpoolingapi.rest.model.CarPosition;
import com.ing.switchtojava.carpoolingapi.rest.model.Position;
import com.ing.switchtojava.carpoolingapi.service.CarService;
import com.ing.switchtojava.carpoolingapi.service.RideService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.validation.Valid;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

    @GetMapping("{id}/track")
    public SseEmitter track(@PathVariable Long id) throws JAXBException, IOException {
        return rideService.track(id);
    }
}

