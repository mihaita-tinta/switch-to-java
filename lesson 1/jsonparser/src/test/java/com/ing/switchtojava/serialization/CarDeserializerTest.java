package com.ing.switchtojava.serialization;

import com.ing.switchtojava.domain.Car;
import org.junit.Test;

public class CarDeserializerTest {

    CarDeserializer deserializer;

    @Test
    public void when_jsonProvided_expect_carWithAllFieldsIsCreated() throws DeserializationException {

        Car car = deserializer.deserialize("{\"id\":\"100\",\"number\":\"B-01-ERU\",\"seats\":\"2\"}");

        //TODO assert the instance properties
    }
}
