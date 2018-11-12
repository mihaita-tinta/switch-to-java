package com.ing.carpooling;

import com.ing.carpooling.aop.RideServiceAspect;
import com.ing.carpooling.domain.Passenger;
import com.ing.carpooling.domain.Ride;
import com.ing.carpooling.domain.RideRequest;
import com.ing.carpooling.repository.CarRepository;
import com.ing.carpooling.repository.RideRepository;
import com.ing.carpooling.service.RideService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RunWith(MockitoJUnitRunner.class)
public class Lesson_4_1_AOPTest {

    @Mock
    Ride ride;

    @Mock
    Passenger passenger;

    @Mock
    RideRequest rideRequest;

    @Mock
    RideRepository rideRepository;

    @Mock
    CarRepository carRepository;

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

    @Test
    public void testManuallyCreateProxies() {
        // create a factory that can generate a proxy for the given target object
        RideService service = new RideService(rideRepository, carRepository);
        AspectJProxyFactory factory = new AspectJProxyFactory(service);
        factory.addAspect(RideServiceAspect.class);
        RideService proxy = factory.getProxy();

        proxy.approve(rideRequest);

    }
}
