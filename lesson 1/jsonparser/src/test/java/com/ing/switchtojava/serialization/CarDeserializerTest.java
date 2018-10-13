package com.ing.switchtojava.serialization;

import com.ing.switchtojava.domain.Car;
import org.junit.Assert;
import org.junit.Test;

public class CarDeserializerTest {

    CarDeserializer deserializer = new CarDeserializer();

    @Test
    public void when_jsonProvided_expect_carWithAllFieldsIsCreated() throws DeserializationException {


        try {
            Car car = deserializer.deserialize("{\"id\":\"100\",\"number\":\"B-01-ERU\",\"seats\":\"2\"}");

            Assert.assertEquals(new Long(100), car.getId());
            Assert.assertEquals("B-01-ERU", car.getNumber());
            Assert.assertEquals(2, car.getSeats());
        } catch (Exception e) {
            System.out.println("Error:: " + e.getMessage());
        }

        //TODO 5 assert the instance properties
    }
}
