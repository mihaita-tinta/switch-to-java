package com.ing.switchtojava.serialization;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


public class ObjectSerializerImpl implements ObjectSerializer{

	@Override
	public String serialize(Object jsonObject) throws SerializationException, JsonProcessingException {
		// TODO 4 Auto-generated method stub

		ObjectMapper objectMap = new ObjectMapper();
		return objectMap.writeValueAsString(jsonObject);
	}

}
