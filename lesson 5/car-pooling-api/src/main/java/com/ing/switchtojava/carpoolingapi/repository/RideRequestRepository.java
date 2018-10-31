package com.ing.switchtojava.carpoolingapi.repository;

import com.ing.switchtojava.carpoolingapi.domain.Passenger;
import com.ing.switchtojava.carpoolingapi.domain.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRequestRepository extends JpaRepository<RideRequest, Long> {
    List<RideRequest> findByPassenger(Passenger passenger);
}
