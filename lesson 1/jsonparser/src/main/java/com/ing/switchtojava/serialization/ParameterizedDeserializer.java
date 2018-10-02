package com.ing.switchtojava.serialization;

public interface ParameterizedDeserializer {

    <T> T deserialize(String string, Class<T> clasz) throws DeserializationException;
	// TODO implement this class
}
