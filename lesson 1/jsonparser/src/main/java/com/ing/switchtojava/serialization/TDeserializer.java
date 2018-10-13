package com.ing.switchtojava.serialization;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


public class TDeserializer implements ParameterizedDeserializer {



    @Override
    public <T> T deserialize(String string, Class<T> clasz) throws DeserializationException {

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(string, clasz);
        } catch (
        JsonParseException e) {
            System.out.println(e.getMessage());
            throw new DeserializationException(e.getMessage());
        } catch (
        JsonMappingException e) {
            System.out.println(e.getMessage());
            throw new DeserializationException(e.getMessage());
        } catch (
        IOException e) {
            System.out.println(e.getMessage());
            throw new DeserializationException(e.getMessage());
        }
    }
}
