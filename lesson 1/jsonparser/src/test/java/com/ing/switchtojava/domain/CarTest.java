package com.ing.switchtojava.domain;

import org.junit.Assert;
import org.junit.Test;


public class CarTest {

    Car car;

    @Test
    public void test() {
//        TODO 1 create instances of Car and assert fields values DONE
        car = new Car(Long.parseLong("12"), "B108HSH",5);
        Assert.assertEquals(Long.parseLong("12"), Long.parseLong(car.getId().toString()));
        Assert.assertEquals("B108HSH", car.getNumber());
        Assert.assertEquals(5,car.getSeats());

    }
}
