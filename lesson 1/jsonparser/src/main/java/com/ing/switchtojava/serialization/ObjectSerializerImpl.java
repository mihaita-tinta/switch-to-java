package com.ing.switchtojava.serialization;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ObjectSerializerImpl implements ObjectSerializer{

	@Override
	public String serialize(Object jsonObject) throws SerializationException {
		ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper.writeValueAsString(jsonObject);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
			throw new SerializationException(e.getMessage());
		} catch (JsonMappingException e) {
			e.printStackTrace();
			throw new SerializationException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new SerializationException(e.getMessage());
		}
	}
}
