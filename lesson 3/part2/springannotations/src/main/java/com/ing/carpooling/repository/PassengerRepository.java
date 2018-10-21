package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Passenger;

import java.util.List;
import java.util.Optional;

public class PassengerRepository implements CrudRepository<Passenger, Long> {
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS PASSENGER ( \n" +
            "   id INT NOT NULL auto_increment, \n" +
            "   firstname VARCHAR(50) NOT NULL, \n" +
            "   lastname VARCHAR(50) NOT NULL, \n" +
            ");";

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
