package com.ing.switchtojava.carpoolingapi.repository;


import com.ing.switchtojava.carpoolingapi.domain.Ride;
import com.ing.switchtojava.carpoolingapi.repository.specification.RideSpecification;
import com.ing.switchtojava.carpoolingapi.repository.specification.SearchCriteria;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RideRepositoryTest {
    private static final Logger log = LoggerFactory.getLogger(RideRepositoryTest.class);
    @Autowired
    RideRepository repository;

    @Sql({"/location.sql", "/passenger.sql", "/ride.sql"})
    @Test
    public void test() {
        Ride ride = repository.findById(1L).get();

        Assert.assertEquals(2, ride.getPassengers().size());
        Assert.assertEquals(Ride.Status.PENDING, ride.getStatus());
    }

    @Sql({"/location.sql", "/passenger.sql", "/ride.sql", "/ride-request.sql"})
    @Test
    public void when_rideWithMultipleRequests_expect_allRideRequestsCanBeFound() {
        Ride ride = repository.findById(1L).get();

        assertEquals(3, ride.getRequests().size());
    }

    @Sql("/ride.sql")
    public void givenFrom_whenGettingRides_thenCorrect() {
        RideSpecification specification = new RideSpecification(new SearchCriteria("from", ":", "Crangasi"));
    }
}
