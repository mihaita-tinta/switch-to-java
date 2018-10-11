package com.ing.switchtojava.serialization;

import com.ing.switchtojava.domain.Car;
import org.junit.Assert;
import org.junit.Test;

public class ObjectSerializerTest {

    ObjectSerializer serializer = new ObjectSerializerImpl();

    @Test
    public void when_serializeCarInstance_expect_correctJson() throws SerializationException {

        Car car = new Car();
        car.setNumber("B-01-ERU");
        car.setId(101L);
        car.setSeats(5);
        String json = serializer.serialize(car);
//        System.out.println(json);

        String strr ="\"{\\\"id\\\":\\\"101\\\",\\\"number\\\":\\\"B-01-ERU\\\",\\\"seats\\\":\\\"5\\\"}\"";
        Assert.assertEquals(json,strr);

        // TODO 4 assert json looks as expected
    }
}
