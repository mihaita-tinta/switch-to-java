package com.ing.switchtojava.serialization;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.IOException;

public interface ObjectDeserializer {

    Object deserialize(String string) throws DeserializationException;

    Object deserializeWithLib(String string) throws JsonParseException, JsonMappingException, IOException;
	
}
