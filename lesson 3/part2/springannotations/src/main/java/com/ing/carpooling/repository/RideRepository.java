package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Ride;
import java.util.List;
import java.util.Optional;

public class RideRepository implements CrudRepository<Ride, Long> {

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS RIDE ( \n" +
            "   id INT NOT NULL auto_increment, \n" +
            "   location_from INT NOT NULL,\n" +
            "   location_to INT NOT NULL,\n" +
            "   when TIMESTAMP WITH TIME ZONE NOT NULL, \n" +
            "   car_id VARCHAR(20) NOT NULL, \n" +
            "   status ENUM('PENDING', 'IN_PROGRESS', 'COMPLETED', 'CANCELED') NOT NULL, \n" +
            ");";

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
