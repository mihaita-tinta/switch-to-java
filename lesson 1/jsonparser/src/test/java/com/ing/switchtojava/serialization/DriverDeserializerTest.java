package com.ing.switchtojava.serialization;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.ing.switchtojava.domain.Car;
import com.ing.switchtojava.domain.Driver;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class DriverDeserializerTest {

    DriverDeserializer deserializer;

    @Test
    public void when_jsonProvided_expect_carWithAllFieldsIsCreated() throws DeserializationException, IOException {

        //TODO 6 assert the instance properties
        Driver driver = deserializer.deserialize("{\"firstName\":\"Dacia\",\"lastName\":\"Renault\",\"cars\":[{\"id\":\"200\",\"number\":\"B-02-ERU\",\"seats\":\"3\"},{\"id\":\"100\",\"number\":\"B-01-ERU\",\"seats\":\"2\"}]}"); // FIXME provide valid JSON
        Assert.assertEquals("Dacia", driver.getFirstName());

}
}
