package com.ing.switchtojava.serialization;

import com.ing.switchtojava.domain.Car;
import org.junit.Assert;
import org.junit.Test;

public class ObjectSerializerTest {

    ObjectSerializer serializer;

    @Test
    public void when_serializeCarInstance_expect_correctJson() throws SerializationException {

        Car car = new Car();
        car.setNumber("B-01-ERU");
        car.setId(101L);
        car.setSeats(5);
        String json = serializer.serialize(car);

        // TODO assert json looks as expected
    }
}
