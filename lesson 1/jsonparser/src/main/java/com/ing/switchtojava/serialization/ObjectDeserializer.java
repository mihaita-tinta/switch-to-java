package com.ing.switchtojava.serialization;

import java.io.IOException;

public interface ObjectDeserializer {

    Object deserialize(String string) throws DeserializationException, IOException;
	
}
