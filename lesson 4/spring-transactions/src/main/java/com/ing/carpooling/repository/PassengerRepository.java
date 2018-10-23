package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Passenger;

import java.util.List;
import java.util.Optional;

public class PassengerRepository implements CrudRepository<Passenger, Long> {


    @Override
    public Passenger save(Passenger instance) {
        // TODO 0 implement Passenger crud
        return null;
    }

    @Override
    public List<Passenger> findAll() {
        // TODO 0 implement Passenger crud
        return null;
    }

    @Override
    public Optional<Passenger> findOne(Long id){
        // TODO 0 implement Passenger crud
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        // TODO 0 implement Passenger crud
    }
}
