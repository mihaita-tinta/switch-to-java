package com.ing.switchtojava.serialization;

import com.ing.switchtojava.domain.Car;
import org.junit.Test;

public class ParameterizedDeserializerTest {

    ParameterizedDeserializer parameterizedDeserializer;

    @Test
    public void when_jsonAndClassProvided_expect_correctInstanceIsCreatedWithAllFields() throws DeserializationException {
        parameterizedDeserializer = new ParametrizedDeserializerImpl();
        Car car = parameterizedDeserializer.deserialize(
                                    "{\"id\":\"100\",\"number\":\"B-01-ERU\",\"seats\":\"2\"}", Car.class);

        // TODO verify everything is ok
    }
}
