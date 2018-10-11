package serialization;

import domain.Car;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleDeserializerTest {

    SimpleDeserializer deserializer = new SimpleDeserializer();
    String carJson = "{" +
            "\"id\":\"2\"," +
            "\"number\":\"IL12ABC\"" +
            "}";


    @Test
    public void when_jsonProvided_expect_carInstanceIsCreatedWithAllPropertiesSet() {
        Car car = deserializer.deserialize(carJson, Car.class);
        //TODO 3 fix test
        assertEquals("IL12ABC", car.getNumber());
        assertEquals(2L, car.getId().longValue());
        assertEquals(0, car.getSeats());
    }
}