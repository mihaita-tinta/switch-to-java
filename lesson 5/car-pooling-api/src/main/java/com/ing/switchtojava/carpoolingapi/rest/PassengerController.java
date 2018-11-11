package com.ing.switchtojava.carpoolingapi.rest;

import com.ing.switchtojava.carpoolingapi.domain.Passenger;
import com.ing.switchtojava.carpoolingapi.service.PassengerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
        return service.findById(id).orElseThrow(() -> new PassengerNotFoundException());
    }

    @PostMapping
    public Passenger save(@Valid @RequestBody Passenger passenger) {
        return service.save(passenger);
    }

    @DeleteMapping
    public void deleteById(Long id) {
        service.deleteById(id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleCarNotFound(PassengerNotFoundException e) {
        log.warn("Car not found", e);
    }
}
