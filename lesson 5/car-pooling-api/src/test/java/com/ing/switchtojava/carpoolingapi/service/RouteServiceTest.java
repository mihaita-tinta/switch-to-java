package com.ing.switchtojava.carpoolingapi.service;

import com.ing.switchtojava.carpoolingapi.domain.Position;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RouteServiceTest {

    @InjectMocks
    RouteService routeService;

    @Test
    public void test() {
        Position p = routeService.getNextPosition();
    }
}
