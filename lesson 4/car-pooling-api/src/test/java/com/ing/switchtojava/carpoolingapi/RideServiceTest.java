package com.ing.switchtojava.carpoolingapi;

import com.ing.switchtojava.carpoolingapi.domain.Ride;
import com.ing.switchtojava.carpoolingapi.repository.RideRepository;
import com.ing.switchtojava.carpoolingapi.service.RideService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static javafx.beans.binding.Bindings.when;

@RunWith(MockitoJUnitRunner.class)
public class RideServiceTest {

    @InjectMocks
    RideService service;

    @Mock
    Ride ride;

    @Mock
    RideRepository rideRepository;

    @Test
    public void test() {
        //when(rideRepository.save(ride));
        service.save(ride);

    }


}
