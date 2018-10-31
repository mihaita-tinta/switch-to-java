package com.ing.switchtojava.carpoolingapi.repository;

import com.ing.switchtojava.carpoolingapi.domain.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

}
