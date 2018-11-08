package com.ing.switchtojava.carpoolingapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ing.switchtojava.carpoolingapi.domain.Ride;
import com.ing.switchtojava.carpoolingapi.repository.RideRepository;
import com.ing.switchtojava.carpoolingapi.rest.RideNotFoundException;

@Service
public class RideService {

    private final RideRepository rideRepository;

    public RideService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }


    public Ride save(Ride ride) {

        return rideRepository.save(ride);
    }

    public List<Ride> findAll() {
        return rideRepository.findAll();
    }

    public Ride findById(Long id) {
        return rideRepository.findById(id).orElseThrow(RideNotFoundException::new);
    }

}
