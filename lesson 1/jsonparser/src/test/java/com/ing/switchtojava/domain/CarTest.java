package com.ing.switchtojava.domain;

import org.junit.Assert;
import org.junit.Test;


public class CarTest {

    Car car;

    @Test
    public void test() {
        car = Car.build().setId(1L).setNumber("B57WEB").setSeats(5);
        Assert.assertEquals(new Long(1L), car.getId());
        Assert.assertEquals("B57WEB", car.getNumber());
        Assert.assertEquals(5, car.getSeats());

        car = Car.build().setId(2L).setNumber("IL58WEB").setSeats(5);
        Assert.assertNotEquals("IL57WEB", car.getNumber());

    }
}
