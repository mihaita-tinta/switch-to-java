package com.ing.switchtojava.serialization;

public interface ObjectSerializer {

    String serialize(Object jsonObject) throws SerializationException;
	
}
