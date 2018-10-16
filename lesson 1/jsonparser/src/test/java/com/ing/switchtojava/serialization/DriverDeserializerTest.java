package com.ing.switchtojava.serialization;

import com.ing.switchtojava.domain.Car;
import com.ing.switchtojava.domain.Driver;
import org.junit.Test;

public class DriverDeserializerTest {

    DriverDeserializer deserializer;

    @Test
    public void when_jsonProvided_expect_carWithAllFieldsIsCreated() throws DeserializationException {


        Driver driver = deserializer.deserialize("{\"firstname\":\"Alex\",\"lastname\":\"Hagiu\", \"firstname\":\"Alex\"");


    }
}
