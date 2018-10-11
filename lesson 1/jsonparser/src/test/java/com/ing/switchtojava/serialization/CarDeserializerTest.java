package com.ing.switchtojava.serialization;

import com.ing.switchtojava.domain.Car;
import org.junit.Assert;
import org.junit.Test;

public class CarDeserializerTest {

    CarDeserializer deserializer =  new CarDeserializer();

    @Test
    public void when_jsonProvided_expect_carWithAllFieldsIsCreated() throws DeserializationException {

        Car car = deserializer.deserialize("{\"id\":\"100\",\"number\":\"B-01-ERU\",\"seats\":\"2\"}");
        System.out.println(car);

//        Assert.assertEquals(car.getId(), 100L);
        Assert.assertEquals(car.getSeats(), 2);
        Assert.assertEquals(car.getNumber(),"B-01-ERU");
        Assert.assertEquals(car.getId(), new Long(100));

        //TODO 5 assert the instance properties


    }
}
