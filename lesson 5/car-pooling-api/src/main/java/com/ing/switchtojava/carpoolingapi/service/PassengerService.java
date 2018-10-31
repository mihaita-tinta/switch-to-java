package com.ing.switchtojava.carpoolingapi.service;

import com.ing.switchtojava.carpoolingapi.domain.Passenger;
import com.ing.switchtojava.carpoolingapi.domain.RideRequest;
import com.ing.switchtojava.carpoolingapi.exception.PassengerNotFoundException;
import com.ing.switchtojava.carpoolingapi.repository.PassengerRepository;
import com.ing.switchtojava.carpoolingapi.repository.RideRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {
    private final PassengerRepository passengerRepository;
    private final RideRequestRepository rideRequestRepository;

    public PassengerService(PassengerRepository repository, RideRequestRepository rideRequestRepository) {
        this.passengerRepository = repository;
        this.rideRequestRepository = rideRequestRepository;
    }

    public List<Passenger> findAll() {
        return passengerRepository.findAll();
    }

    public Passenger findById(Long id) {
        return passengerRepository.findById(id).orElseThrow(PassengerNotFoundException::new);
    }

    public Passenger save(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    public List<RideRequest> findRideRequests(Long id) {
        Passenger passenger = passengerRepository.findById(id).orElseThrow(PassengerNotFoundException::new);
        return rideRequestRepository.findByPassenger(passenger);
    }
}
