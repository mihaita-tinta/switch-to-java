package com.ing.switchtojava.serialization;

import com.ing.switchtojava.domain.Car;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class CarDeserializerTest {

    CarDeserializer deserializer = new CarDeserializer();

    @Test
    public void when_jsonProvided_expect_carWithAllFieldsIsCreated() throws DeserializationException, IOException {

        Car car = deserializer.deserialize("{\"id\":\"100\",\"number\":\"B-01-ERU\",\"seats\":\"2\"}");
        Assert.assertEquals("B-01-ERU", car.getNumber());

        //TODO 5 assert the instance properties DONE
    }
}
