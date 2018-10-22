package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Ride;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class RideRepository implements CrudRepository<Ride, Long> {
    private  static final Logger log = LoggerFactory.getLogger(PassengerRepository.class);





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
