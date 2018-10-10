package com.ing.switchtojava.serialization;

import com.ing.switchtojava.domain.Car;
import com.ing.switchtojava.domain.Driver;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.IIOException;
import java.io.IOException;

public class DriverDeserializer implements ObjectDeserializer{

	@Override
	public Driver deserialize(String string) throws DeserializationException, IOException {
		// TODO 6 Auto-generated method stub
		ObjectMapper DesJson = new ObjectMapper();
		return DesJson.readValue(string, Driver.class);
	}

}
