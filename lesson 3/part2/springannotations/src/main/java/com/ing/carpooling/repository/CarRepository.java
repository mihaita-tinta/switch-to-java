package com.ing.carpooling.repository;

import com.ing.carpooling.domain.*;

import java.util.List;
import java.util.Optional;

public class CarRepository implements CrudRepository<Car, Long> {
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS CAR ( \n" +
            "   id INT NOT NULL auto_increment, \n" +
            "   number VARCHAR(7) NOT NULL, \n" +
            "   seats INT NOT NULL, \n" +
            "   driver_id INT NOT NULL\n" +
            ");";

    @Override
    public Car save(Car instance) {
        // TODO 0 implement Car crud
        return null;
    }

    @Override
    public List<Car> findAll() {
        // TODO 0  implement Car crud
        return null;
    }

    @Override
    public Optional<Car> findOne(Long id) {
        // TODO 0  implement Car crud
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        // TODO 0 implement Car crud
    }
}
