package com.adventureit.adventureservice.exceptions;

/**
 * The NullFieldException
 * This exception is is thrown when a field in a request DTO is null
 */
public class NullFieldException extends RuntimeException {
    public NullFieldException(String message) {
        super(message);
    }
}
