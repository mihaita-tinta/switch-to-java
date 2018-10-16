package com.ing.switchtojava.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.switchtojava.domain.Car;

import java.io.IOException;

public class ObjectSerializerImpl implements ObjectSerializer{

	@Override
	public String serialize(Object jsonObject) throws JsonProcessingException, SerializationException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String serialized = new String();
		return mapper.writeValueAsString((Car) jsonObject);
		// TODO 4 Auto-generated method stub DONE

	}

}
