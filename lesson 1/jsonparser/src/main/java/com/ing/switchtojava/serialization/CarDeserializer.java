package com.ing.switchtojava.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.switchtojava.domain.Car;

import java.io.IOException;

public class CarDeserializer implements ObjectDeserializer{

	@Override
	public Car deserialize(String string) throws DeserializationException, IOException {
		// TODO 5 Auto-generated method stub DONE
        ObjectMapper mapper = new ObjectMapper();
        Car car = mapper.readValue(string, Car.class);
		return car;
	}

}
