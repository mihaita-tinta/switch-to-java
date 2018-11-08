package com.ing.switchtojava.carpoolingapi.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ing.switchtojava.carpoolingapi.domain.Passenger;
import com.ing.switchtojava.carpoolingapi.domain.RideRequest;
import com.ing.switchtojava.carpoolingapi.repository.PassengerRepository;
import com.ing.switchtojava.carpoolingapi.repository.RideRequestRepository;
import com.ing.switchtojava.carpoolingapi.rest.PassengerNotFoundException;

@Service
public class PassengerService {
    static Logger LOGGER = LoggerFactory.getLogger(PassengerService.class);
    private final PassengerRepository passengerRepository;
    private final RideRequestRepository rideRequestRepository;

    public PassengerService(PassengerRepository passengerRepository, RideRequestRepository rideRequestRepository) {
        this.passengerRepository = passengerRepository;
        this.rideRequestRepository = rideRequestRepository;
    }

    public List<Passenger> findAll() {
        return passengerRepository.findAll();
    }

    public Passenger findById(Long id) {
        return passengerRepository.findById(id).orElseThrow(PassengerNotFoundException::new);
    }


    public List<RideRequest> findPassengerRides(Long id) {
        Passenger passenger = passengerRepository.findById(id).orElseThrow(PassengerNotFoundException::new);

        return rideRequestRepository.findByPassenger(passenger);
    }

    public Passenger save(Passenger passenger) {
        return passengerRepository.save(passenger);
    }


}
