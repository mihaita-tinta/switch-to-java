package com.ing.switchtojava.carpoolingapi.service;


import com.ing.switchtojava.carpoolingapi.domain.Ride;
import com.ing.switchtojava.carpoolingapi.repository.RideRepository;
import com.ing.switchtojava.carpoolingapi.rest.model.CarPosition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.vesalainen.jaxb.gpx.GpxType;
import org.junit.Assert;

import javax.xml.bind.JAXBException;
import java.io.IOException;

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

    @Test
    public void testLoadCoordinatesFromFile() throws JAXBException, IOException {
        CarPosition carPosition = service.loadCoordinatesFromFile( 1L);
        carPosition.getPositions()
                .forEach(p -> System.out.println("Pos "+ p.getLat() + ", " + p.getLon()));

        Assert.assertNotNull(carPosition.getPositions());
    }

}
