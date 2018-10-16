package com.ing.switchtojava.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface ObjectSerializer {

    String serialize(Object jsonObject) throws JsonProcessingException, SerializationException, IOException;
	
}
