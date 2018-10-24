package com.ing.carpooling.service;

import com.ing.carpooling.domain.Location;
import com.ing.carpooling.repository.LocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocationService {

    private final LocationRepository repository;

    public LocationService(LocationRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Location save(Location location) {
        Location saved = repository.save(location);
        return saved;
    }
}
