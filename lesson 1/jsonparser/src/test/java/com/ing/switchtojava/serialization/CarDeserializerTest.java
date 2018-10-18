package com.ing.switchtojava.serialization;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ing.switchtojava.domain.Car;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class CarDeserializerTest {

    CarDeserializer deserializer;

    @Test
    public void when_jsonProvided_expect_carWithAllFieldsIsCreated() throws DeserializationException, JsonParseException, JsonMappingException, IOException {

        deserializer = new CarDeserializer();
        Car car = deserializer.deserialize("{\"id\":\"100\",\"number\":\"B-01-ERU\",\"seats\":\"2\"}");

        System.out.println(car.toString());
        Assert.assertEquals(100L, (long) car.getId());
        Assert.assertEquals("B-01-ERU", car.getNumber());
        Assert.assertEquals(2, car.getSeats());

        Car carLib = deserializer.deserializeWithLib("{\"id\":\"100\",\"number\":\"B-01-ERU\",\"seats\":\"2\"}");
        System.out.println(carLib.toString());
        Assert.assertEquals(100L, (long) carLib.getId());
        Assert.assertEquals("B-01-ERU", carLib.getNumber());
        Assert.assertEquals(2, carLib.getSeats());

        //TODO 5 assert the instance properties
    }
}
