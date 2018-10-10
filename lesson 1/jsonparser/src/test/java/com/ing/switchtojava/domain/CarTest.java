package com.ing.switchtojava.domain;

import org.junit.Assert;
import org.junit.Test;


public class CarTest {

   // Car car;

    @Test
    public void test() {
//        TODO 1 create instances of Car and assert fields values
        Car builderCar = new Car.BuilderCar().number("AG-01-GXD").id(101L).seats(5).build();
        Assert.assertEquals("AG-01-GXD",builderCar.getNumber());
        Assert.assertEquals(5,builderCar.getSeats());
        Assert.assertEquals(101L, builderCar.getId().longValue());

    }
}
