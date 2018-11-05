package com.ing.switchtojava.carpoolingapi.service;

import com.ing.switchtojava.carpoolingapi.domain.Passenger;
import com.ing.switchtojava.carpoolingapi.domain.Ride;
import com.ing.switchtojava.carpoolingapi.domain.RideRequest;
import com.ing.switchtojava.carpoolingapi.repository.PassengerRepository;
import com.ing.switchtojava.carpoolingapi.repository.RideRepository;
import com.ing.switchtojava.carpoolingapi.repository.RideRequestRepository;
import com.ing.switchtojava.carpoolingapi.rest.PassengerNotFoundException;
import com.ing.switchtojava.carpoolingapi.rest.RideNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final RideRequestRepository rideRequestRepository;
    private final RideRepository rideRepository;

    public PassengerService(PassengerRepository passengerRepository, RideRequestRepository rideRequestRepository, RideRepository rideRepository){
        this.passengerRepository = passengerRepository;
        this.rideRequestRepository = rideRequestRepository;
        this.rideRepository = rideRepository;
    }

    public Iterable<Passenger> findAll(){
        return passengerRepository.findAll();
    }

    public Passenger findById(Long id){
        return passengerRepository.findById(id)
                .orElseThrow(()-> new PassengerNotFoundException());
    }

    public Passenger save(Passenger passenger){
        return passengerRepository.save(passenger);
    }

    public List<RideRequest> listRequestsByPassenger(Long id){
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(()-> new PassengerNotFoundException());
        return rideRequestRepository.findByPassenger(passenger);
    }

    public Ride joinRide(Long passengerId, Long rideId ){
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(()-> new PassengerNotFoundException());
        System.out.println("#################################################");
        System.out.println(passenger);
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(()-> new RideNotFoundException());
        System.out.println("#################################################");
        System.out.println(ride);
        ride.getPassengers().add(passenger);
        RideRequest rideRequest = rideRequestRepository.findByPassengerAndRide(passenger,ride);
        System.out.println(rideRequest);
        rideRequest.setStatus(RideRequest.Status.ACCEPTED);

        return rideRepository.save(ride);
    }



}
