package com.ing.switchtojava.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.switchtojava.domain.Car;
import com.ing.switchtojava.domain.Driver;

import java.io.IOException;

public class CarDeserializer implements ObjectDeserializer{

	@Override
	public Car deserialize(String string) throws DeserializationException, IOException {
		// TODO 5 Auto-generated method stub
		ObjectMapper DesJson = new ObjectMapper();
		return DesJson.readValue(string, Car.class);
	}

}
