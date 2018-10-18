package com.ing.ayo.service;

import com.ing.ayo.domain.Location;
import com.ing.ayo.repository.CarRepository;
import com.ing.ayo.repository.RideRepository;
import com.ing.ayo.service.RideService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RideServiceTest {

    @InjectMocks
    RideService rideService;

    @Mock
    CarRepository carRepository;

    @Mock
    RideRepository rideRepository;

    @Test
    public void test() {

    }
}
