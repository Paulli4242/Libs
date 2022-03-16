package org.ccffee.extension;

public class InvalidReturnTypeException extends RuntimeException{
    public InvalidReturnTypeException(Class<?> expectedType) {
        super("Expected "+expectedType.getName());
    }
}
