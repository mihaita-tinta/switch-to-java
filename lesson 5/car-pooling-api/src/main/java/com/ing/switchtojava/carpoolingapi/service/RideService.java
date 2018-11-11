package com.ing.switchtojava.carpoolingapi.service;

import com.ing.switchtojava.carpoolingapi.domain.Ride;
import com.ing.switchtojava.carpoolingapi.repository.RideRepository;
import com.ing.switchtojava.carpoolingapi.rest.PassengerNotFoundException;
import com.ing.switchtojava.carpoolingapi.rest.RideNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RideService {

    private final RideRepository rideRepository;

    public RideService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }


    public Ride save(Ride ride) {
        return rideRepository.save(ride);
    }

    public Iterable<Ride> findAll(){
        return  rideRepository.findAll();
    }

    public Ride findOne(Long id){
        return rideRepository.findById(id)
                .orElseThrow(()-> new RideNotFoundException());
    }
}
