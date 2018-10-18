package com.ing.switchtojava.serialization;

import com.ing.switchtojava.domain.Car;
import com.ing.switchtojava.domain.Driver;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ParametrizedDeserializerImpl implements ParameterizedDeserializer{

    @Override
    public <T> T deserialize(String string, Class<T> clasz) throws DeserializationException {
        ObjectDeserializer deserializer;

        try {
            Method m = clasz.getDeclaredMethod("deserializer", null);
            Object meth = m.invoke(clasz.newInstance(), null);
            return (T) ((ObjectDeserializer) meth).deserialize(string);
        } catch (Exception e) {
            System.out.println("Exception when deserializing");
        }

        return null;

        // another method - pretty dummy
//        if (clasz == Car.class) {
//            deserializer = new CarDeserializer();
//        }
//        else if (clasz == Driver.class) {
//            deserializer = new DriverDeserializer();
//        }
//        else {
//            throw new DeserializationException("This is not a known class!");
//        }

//        return (T) deserializer.deserialize(string);
    }
}
