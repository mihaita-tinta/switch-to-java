package com.ing.switchtojava.carpoolingapi.service;


import com.ing.switchtojava.carpoolingapi.domain.CarPosition;
import com.ing.switchtojava.carpoolingapi.domain.Ride;
import com.ing.switchtojava.carpoolingapi.repository.DriverRepository;
import com.ing.switchtojava.carpoolingapi.repository.RideRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.vesalainen.jaxb.gpx.GpxType;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarPositionServiceTest {

//    @InjectMocks
//    CarPositionService service;

    @Test
    public void test() throws IOException, JAXBException {

//        service.carPosition();

        for (int i = 0; i < 100; i++) {
//            CarPosition pos = service.getNextPosition();
//            System.out.println(pos.getLatitude() + ", " + pos.getLongitude());
        }
    }

}