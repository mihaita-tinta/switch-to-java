package com.ing.switchtojava.carpoolingapi;


import com.ing.switchtojava.carpoolingapi.domain.Location;
import com.ing.switchtojava.carpoolingapi.repository.LocationRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LocationRepositoryTest {

    @Autowired
    LocationRepository repository;

    @Test
    public void test() {

        Location location = new Location();
        location.setAddress("aleea lacul morii nr. 4");
        location.setCity("Bucuresti");
        location.setZip("123-123");
        Location dbLocation = repository.saveAndFlush(location);
        Assert.assertNotNull(dbLocation.getId());
    }

    @Test
    public void testFind() {

        Location location = new Location();
        location.setAddress("aleea lacul morii nr. 4");
        location.setCity("Bucuresti");
        location.setZip("123-123");
        Location dbLocation = repository.save(location);

        Optional<Location> fromFind = repository.findById(dbLocation.getId());
        Assert.assertEquals(dbLocation.getId(), fromFind.get().getId());
    }
}
