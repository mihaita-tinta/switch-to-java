package com.ing.ayo.repository;

import com.ing.ayo.domain.Driver;
import com.ing.ayo.domain.Location;
import com.ing.ayo.domain.Ride;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

public class RideRepository {

    public Ride save(Ride ride) {
        // FIXME in memory save instance. generate unique id
        return ride;
    }

    public List<Ride> findAllByDriver(Driver driver) {
        // FIXME search rides by driver
        return Collections.emptyList();
    }

    public List<Ride> findAll(Location from, Location to, ZonedDateTime start, ZonedDateTime end) {
        // FIXME search rides by driver
        return Collections.emptyList();
    }
}
