package com.ing.switchtojava.serialization;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ing.switchtojava.domain.Car;
import com.ing.switchtojava.domain.Driver;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DriverDeserializerTest {

    DriverDeserializer deserializer;

    @Test
    public void when_jsonProvided_expect_carWithAllFieldsIsCreated() throws DeserializationException, JsonParseException, JsonMappingException, IOException {
        deserializer = new DriverDeserializer();
        Driver driver = deserializer.deserialize("{\"lastName\":\"Popescu\", \"cars\":[{\"id\":\"100\",\"number\":\"B-01-ERU\",\"seats\":\"2\"},{\"id\":\"200\",\"number\":\"B-02-ERU\",\"seats\":\"4\"}],\"firstName\":\"Ion\"}"); // FIXME provide valid JSON

        Driver driverLib = deserializer.deserializeWithLib("{\"lastName\":\"Popescu\", \"cars\":[{\"id\":\"100\",\"number\":\"B-01-ERU\",\"seats\":\"2\"},{\"id\":\"200\",\"number\":\"B-02-ERU\",\"seats\":\"4\"}],\"firstName\":\"Ion\"}"); // FIXME provide valid JSON

        //TODO 6 assert the instance properties

        Car car1 = new Car();
        car1.setId(100L);
        car1.setNumber("B-01-ERU");
        car1.setSeats(2);

        Car car2 = new Car();
        car2.setId(200L);
        car2.setNumber("B-02-ERU");
        car2.setSeats(4);

        ArrayList<Car> cars = new ArrayList<Car>();
        cars.add(car1);
        cars.add(car2);

        Assert.assertEquals("Ion", driver.getFirstName());
        Assert.assertEquals("Popescu", driver.getLastName());
        Assert.assertEquals(cars.toString(), driver.getCars().toString());

        Assert.assertEquals("Ion", driverLib.getFirstName());
        Assert.assertEquals("Popescu", driverLib.getLastName());
        Assert.assertEquals(cars.toString(), driverLib.getCars().toString());
    }
}
