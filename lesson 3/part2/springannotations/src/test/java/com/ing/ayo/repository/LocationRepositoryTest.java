package com.ing.ayo.repository;

import com.ing.ayo.domain.Location;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class LocationRepositoryTest {

    LocationRepository repository = new LocationRepository();

    @Test
    public void test() {
        Location location = new Location();
        location.setAddress("S-Park, A1, Poligrafiei 123");
        Location saved = repository.save(location);
        assertNotNull(saved.getId());

    }
}
