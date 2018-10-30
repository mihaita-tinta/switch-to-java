package com.ing.switchtojava.carpoolingapi.service;


import com.ing.switchtojava.carpoolingapi.domain.Ride;
import com.ing.switchtojava.carpoolingapi.repository.RideRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RideServiceTest {

    @InjectMocks
    RideService service;

    @Mock
    RideRepository rideRepository;

    @Mock
    Ride ride;

    @Test
    public void test() {
        when(rideRepository.save(any())).thenReturn(ride);

        service.save(ride);

        verify(rideRepository).save(any());
    }

}
