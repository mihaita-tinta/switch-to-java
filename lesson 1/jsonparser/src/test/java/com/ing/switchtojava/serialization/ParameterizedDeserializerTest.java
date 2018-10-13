package com.ing.switchtojava.serialization;

import com.ing.switchtojava.domain.Car;
import org.junit.Assert;
import org.junit.Test;

public class ParameterizedDeserializerTest {

    ParameterizedDeserializer parameterizedDeserializer;

    @Test
    public void when_jsonAndClassProvided_expect_correctInstanceIsCreatedWithAllFields() throws DeserializationException {

        parameterizedDeserializer = new TDeserializer();
        Car car = parameterizedDeserializer.deserialize(
                                    "{\"id\":\"100\",\"number\":\"B-01-ERU\",\"seats\":\"2\"}", Car.class);

        Assert.assertTrue(100L==car.getId());
        Assert.assertEquals("B-01-ERU", car.getNumber());
        Assert.assertEquals(2, car.getSeats());
        // TODO verify everything is ok
    }
}
