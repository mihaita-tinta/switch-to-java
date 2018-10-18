package com.ing.switchtojava.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ing.switchtojava.domain.Car;
import com.ing.switchtojava.domain.Driver;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ObjectSerializerTest {

    ObjectSerializer serializer;

    @Test
    public void when_serializeCarInstance_expect_correctJson() throws SerializationException, JsonProcessingException {

        Car car = new Car();
        car.setNumber("B-01-ERU");
        car.setId(101L);
        car.setSeats(5);
        Driver driver = new Driver();
        driver.setFirstName("Gheorghe");
        driver.setLastName("Ionescu");
        List<Car> cars = new ArrayList<Car>();
        cars.add(car);
        driver.setCars(cars);
        serializer = new ObjectSerializerImpl();

        String carJson = serializer.serialize(car);
        System.out.println(carJson);

        String driverJson = serializer.serialize(driver);
        System.out.println(driverJson);

        Assert.assertEquals("{\"firstName\"=\"Gheorghe\", \"lastName\"=\"Ionescu\", \"cars\"=[{\"id\"=\"101\", \"number\"=\"B-01-ERU\", \"seats\"=\"5\"}]}", driverJson);


        String carJsonLib = serializer.serializeWithLib(car);
        System.out.println(carJsonLib);

        String driverJsonLib = serializer.serializeWithLib(driver);
        System.out.println(driverJsonLib);

        Assert.assertEquals("{\n" +
                "  \"firstName\" : \"Gheorghe\",\n" +
                "  \"lastName\" : \"Ionescu\",\n " +
                " \"cars\" : [ {\n" +
                    "    \"id\" : 101,\n" +
                    "    \"number\" : \"B-01-ERU\",\n" +
                    "    \"seats\" : 5\n" +
                "  } ]\n" +
                "}", driverJsonLib);


        // TODO 4 assert json looks as expected
    }
}
