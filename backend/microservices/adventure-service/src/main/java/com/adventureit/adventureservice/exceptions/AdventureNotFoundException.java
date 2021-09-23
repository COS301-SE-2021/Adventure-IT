package com.adventureit.adventureservice.exceptions;

/**
 * AdventureNotFoundException
 * This exception is is thrown when an Adventure cannot be found in the database
 */
public class AdventureNotFoundException extends RuntimeException {
    public AdventureNotFoundException(String message) {
        super(message);
    }
}
