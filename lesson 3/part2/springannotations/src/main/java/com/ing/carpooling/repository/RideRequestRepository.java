package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Passenger;
import com.ing.carpooling.domain.RideRequest;

import java.util.List;
import java.util.Optional;

public class RideRequestRepository implements CrudRepository<RideRequest, Long> {


    @Override
    public RideRequest save(RideRequest instance) {
        // TODO 0 implement RideRequest crud
        return null;
    }

    @Override
    public List<RideRequest> findAll() {
        // TODO 0 implement RideRequest crud
        return null;
    }

    @Override
    public Optional<RideRequest> findOne(Long id) {
        // TODO 0 implement RideRequest crud
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        // TODO 0 implement RideRequest crud

    }
}
