package com.ing.carpooling;

import com.ing.carpooling.domain.Passenger;
import com.ing.carpooling.domain.Ride;
import com.ing.carpooling.domain.RideRequest;
import com.ing.carpooling.service.RideService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RunWith(MockitoJUnitRunner.class)
public class Lesson_4_1_AOPTest {

    @Mock
    Ride ride;

    @Mock
    Passenger passenger;

    @Mock
    RideRequest rideRequest;

    @Test
    public void test() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        RideService service = context.getBean(RideService.class);

        service.finish(ride);

    }

    @Test
    public void testRideRequestAudit() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        RideService service = context.getBean(RideService.class);

        service.join(passenger, ride);

    }

    @Test
    public void testBeforeApprove() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        RideService service = context.getBean(RideService.class);

        service.approve(rideRequest);

    }
}
