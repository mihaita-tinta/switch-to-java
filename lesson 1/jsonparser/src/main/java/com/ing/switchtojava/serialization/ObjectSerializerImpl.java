package com.ing.switchtojava.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectSerializerImpl implements ObjectSerializer{

	@Override
	public String serialize(Object jsonObject) throws SerializationException {
		// TODO 4 Auto-generated method stub
		return jsonObject.toString();
	}

	@Override
	public String serializeWithLib(Object jsonObject) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
	}

}
