package com.ing.switchtojava.serialization;

import com.ing.switchtojava.domain.Car;
import org.junit.Assert;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;

public class ObjectSerializerTest {

    ObjectSerializer serializer;

    @Test
    public void when_serializeCarInstance_expect_correctJson() throws SerializationException, JsonProcessingException {

        Car car = new Car.BuilderCar().id(101L).number("B-01-ERU").seats(5).build();
        serializer = new ObjectSerializerImpl();
      //  car.setNumber("B-01-ERU");
      //  car.setId(101L);
     //   car.setSeats(5);
        String json = serializer.serialize(car);
        Assert.assertEquals("{\"id\":101,\"number\":\"B-01-ERU\",\"seats\":5}", json);

        // TODO 4 assert json looks as expected
    }
}
