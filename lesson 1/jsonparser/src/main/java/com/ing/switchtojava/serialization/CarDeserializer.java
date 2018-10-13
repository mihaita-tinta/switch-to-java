package com.ing.switchtojava.serialization;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.switchtojava.domain.Car;

import java.io.IOException;

public class CarDeserializer implements ObjectDeserializer{

	@Override
	public Car deserialize(String string) throws DeserializationException {
		ObjectMapper mapper = new ObjectMapper();

		try {
			Car car = mapper.readValue(string, Car.class);
			return car;
		} catch (JsonParseException e) {
			System.out.println(e.getMessage());
			throw new DeserializationException(e.getMessage());
		} catch (JsonMappingException e) {
			System.out.println(e.getMessage());
			throw new DeserializationException(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw new DeserializationException(e.getMessage());
		}
	}

}
