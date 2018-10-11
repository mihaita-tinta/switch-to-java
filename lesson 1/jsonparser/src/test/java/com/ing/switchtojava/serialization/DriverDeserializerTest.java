package com.ing.switchtojava.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.switchtojava.domain.Car;
import com.ing.switchtojava.domain.Driver;
//import org.codehaus.jackson.map.ObjectMapper;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DriverDeserializerTest {

    DriverDeserializer deserializer;

    @Test
    public void when_jsonProvided_expect_carWithAllFieldsIsCreated() throws DeserializationException {

        ObjectMapper mapperObj = new ObjectMapper();

        Car c = new Car.Builder()
                .setId(101L)
                .setNumber("B-01-ERU")
                .setSeats(new Integer(5))
                .build();

        Car c2 = new Car.Builder()
                .setId(19L)
                .setNumber("IL-01-ERU")
                .setSeats(new Integer(3))
                .build();

        Driver driv = new Driver();
        driv.setFirstName("Ion");
        driv.setLastName("Vasile");
        List<Car> l = new ArrayList<Car>();
        l.add(c);
        l.add(c2);

        driv.setCars(l);
        String jsonStr ="";
        try{
            jsonStr = mapperObj.writeValueAsString(driv);
            System.out.println(jsonStr);

        }catch (IOException e){
            e.printStackTrace();
        }

        String s = "{\"firstName\":\"Ion\",\"lastName\":\"Vasile\",\"cars\":[{\"id\":101,\"number\":\"B-01-ERU\",\"seats\":5}]}";
        String a = jsonStr;
        ObjectMapper mapperObj2 = new ObjectMapper();

        try{
            Driver jsonStr2 = mapperObj2.readValue(a, Driver.class);
            System.out.println(jsonStr2);

        }catch (IOException e){
            e.printStackTrace();
        }

        //Driver driver = deserializer.deserialize("{\"firstName\":\"Ion\",\"lastName\":\"Vasile\",\"cars\":[{\"id\":101,\"number\":\"B-01-ERU\",\"seats\":5}]}"); // FIXME provide valid JSON


        //TODO 6 assert the instance properties

    }
}
