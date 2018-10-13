package com.ing.switchtojava.serialization;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.switchtojava.domain.Car;
import com.ing.switchtojava.domain.Driver;

import java.io.IOException;

public class DriverDeserializer implements ObjectDeserializer{

	@Override
	public Driver deserialize(String string) throws DeserializationException {
		ObjectMapper mapper = new ObjectMapper();

		try {
			Driver driver = mapper.readValue(string, Driver.class);
			return driver;
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
