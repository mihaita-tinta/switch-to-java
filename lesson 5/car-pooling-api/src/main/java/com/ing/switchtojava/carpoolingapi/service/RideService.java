package com.ing.switchtojava.carpoolingapi.service;

import com.ing.switchtojava.carpoolingapi.domain.Passenger;
import com.ing.switchtojava.carpoolingapi.domain.Ride;
import com.ing.switchtojava.carpoolingapi.exception.PassengerNotFoundException;
import com.ing.switchtojava.carpoolingapi.exception.RideNotFoundException;
import com.ing.switchtojava.carpoolingapi.repository.PassengerRepository;
import com.ing.switchtojava.carpoolingapi.repository.RideRepository;
import org.springframework.stereotype.Service;

@Service
public class RideService {

    private final RideRepository rideRepository;

    private final PassengerRepository passengerRepository;

    public RideService(RideRepository rideRepository, PassengerRepository passengerRepository) {
        this.rideRepository = rideRepository;
        this.passengerRepository = passengerRepository;
    }


    public Ride save(Ride ride) {

        return rideRepository.save(ride);
    }

    public Ride join(Long passengerId, Long rideId) {
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow(PassengerNotFoundException::new);
        Ride ride = rideRepository.findById(rideId).orElseThrow(RideNotFoundException::new);
        ride.getPassengers().add(passenger);
        return rideRepository.save(ride);
    }
}
