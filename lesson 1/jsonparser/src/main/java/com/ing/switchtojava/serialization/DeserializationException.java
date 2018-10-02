package com.ing.switchtojava.serialization;

public class DeserializationException extends Exception {
    public DeserializationException(String message) {
        super(message);
    }

    public DeserializationException(String message, Exception cause) {
        super(message, cause);
    }
}
