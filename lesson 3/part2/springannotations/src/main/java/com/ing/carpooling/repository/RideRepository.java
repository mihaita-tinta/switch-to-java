package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Ride;
import java.util.List;
import java.util.Optional;

public class RideRepository implements CrudRepository<Ride, Long> {

    @Override
    public Ride save(Ride instance) {
        // TODO 0 implement Ride crud
        return null;
    }

    @Override
    public List<Ride> findAll() {
        // TODO 0 implement Ride crud
        return null;
    }

    @Override
    public Optional<Ride> findOne(Long id) {
        // TODO 0 implement Ride crud
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        // TODO 0 implement Ride crud

    }
}
