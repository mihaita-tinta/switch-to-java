package com.ing.switchtojava.serialization;

public interface ObjectDeserializer {

    Object deserialize(String string) throws DeserializationException;
	
}
