package com.ing.switchtojava.serialization;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ObjectSerializer {

    String serialize(Object jsonObject) throws SerializationException, JsonProcessingException;
	
}
