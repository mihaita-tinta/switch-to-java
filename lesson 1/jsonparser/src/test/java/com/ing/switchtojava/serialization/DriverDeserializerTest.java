package com.ing.switchtojava.serialization;

import com.ing.switchtojava.domain.Car;
import com.ing.switchtojava.domain.CarBuilder;
import com.ing.switchtojava.domain.Driver;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DriverDeserializerTest {

    DriverDeserializer deserializer = new DriverDeserializer();

    @Test
    public void when_jsonProvided_expect_carWithAllFieldsIsCreated() throws DeserializationException {

        ObjectSerializer os = new ObjectSerializerImpl();
        String driverJson   = "";
        // Create Car List
        List<Car> myCars    = new ArrayList<Car>();
        CarBuilder carBuilder = new CarBuilder();
        myCars.add(carBuilder.setId(1L).setNumber("B-10-ALX").setSeats(4).build());
        // Create Driver
        Driver driver1      = new Driver();
        driver1.setFirstName("Cosmin");
        driver1.setLastName("Pricope");
        driver1.setCars(myCars);

        try {

            driverJson      = os.serialize(driver1);
            Driver driver   = deserializer.deserialize(driverJson); // FIXME provide valid JSON

            Assert.assertEquals("Cosmin", driver.getFirstName());
            Assert.assertEquals("Pricope", driver.getLastName());
            Assert.assertEquals(1, driver.getCars().size());

        } catch (Exception e) {
            throw new DeserializationException(e.getMessage());
        }
    }
}
