package com.ing.switchtojava.carpoolingapi;


import com.ing.switchtojava.carpoolingapi.domain.Ride;
import com.ing.switchtojava.carpoolingapi.domain.RideRequest;
import com.ing.switchtojava.carpoolingapi.repository.PassengerRepository;
import com.ing.switchtojava.carpoolingapi.repository.RideRepository;
import com.ing.switchtojava.carpoolingapi.repository.RideRequestRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RideRequestRepositoryTest {
    private static final Logger log = LoggerFactory.getLogger(RideRequestRepositoryTest.class);
    @Autowired
    RideRequestRepository repository;

    @Autowired
    RideRepository rideRepository;

    @Autowired
    PassengerRepository passengerRepository;

    @Sql({"/location.sql", "/passenger.sql", "/ride.sql"})
    @Test
    public void test() {
        RideRequest request = new RideRequest();
        request.setStatus(RideRequest.Status.ACCEPTED);
        Ride ride = rideRepository.findById(1L).get();
        request.setRide(ride);
        request.setPassenger(passengerRepository.findById(1L).get());

        repository.saveAndFlush(request);

        Assert.assertNotNull(request.getId());
        Assert.assertEquals(RideRequest.Status.ACCEPTED, request.getStatus());
    }

}
