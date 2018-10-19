package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Driver;

import java.util.List;
import java.util.Optional;

public class DriverRepository implements CrudRepository<Driver, Long> {


    @Override
    public Driver save(Driver instance) {
        // TODO 0 implement Driver crud
        return null;
    }

    @Override
    public List<Driver> findAll() {
        // TODO 0 implement Driver crud
        return null;
    }

    @Override
    public Optional<Driver> findOne(Long id) {
        // TODO 0 implement Driver crud
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        // TODO 0 implement Driver crud

    }
}
