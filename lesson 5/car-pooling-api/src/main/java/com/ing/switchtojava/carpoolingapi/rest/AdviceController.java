package com.ing.switchtojava.carpoolingapi.rest;

import com.ing.switchtojava.carpoolingapi.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AdviceController {
    private static final Logger log = LoggerFactory.getLogger(AdviceController.class);


    @ExceptionHandler(LocationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleLocationNotFound(LocationNotFoundException e) {
        log.warn("handleLocationNotFound - " + e);
    }

    @ExceptionHandler(DriverNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleDriverNotFound(DriverNotFoundException e) {
        log.warn("handleDriverNotFound - " + e);
    }

    @ExceptionHandler(PassengerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handlePassengerNotFound(PassengerNotFoundException e) {
        log.warn("handlePassengerNotFound - " + e);
    }

    @ExceptionHandler(RideNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleRideNotFound(RideNotFoundException e) {
        log.warn("handleRideNotFound - " + e);
    }

    @ExceptionHandler(CarNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleCarNotFound(CarNotFoundException e) {
        log.warn("handleCarNotFound - " + e);
    }
}
