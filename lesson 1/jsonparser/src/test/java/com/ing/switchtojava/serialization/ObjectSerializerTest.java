package com.ing.switchtojava.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ing.switchtojava.domain.Car;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ObjectSerializerTest {

    ObjectSerializer serializer = new ObjectSerializerImpl();

    @Test
    public void when_serializeCarInstance_expect_correctJson() throws JsonProcessingException, SerializationException, IOException {

        Car car = new Car();
        car.setNumber("B-01-ERU");
        car.setId(101L);
        car.setSeats(5);
        String json = serializer.serialize(car);


        // TODO 4 assert json looks as expected DONE
    }
}
