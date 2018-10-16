package com.ing.ayo.repository;

import com.ing.ayo.domain.Car;
import com.ing.ayo.domain.Location;

import java.util.Optional;

public class LocationRepository {

    public Location save(Location location) {
        // FIXME save car and generate unique id
        return location;
    }

    public Optional<Location> findOne(Long id) {

        // FIXME find car by id
        return Optional.empty();
    }
}
