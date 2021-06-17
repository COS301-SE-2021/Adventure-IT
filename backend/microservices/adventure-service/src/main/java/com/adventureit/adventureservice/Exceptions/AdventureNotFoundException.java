package com.adventureit.adventureservice.Exceptions;

public class AdventureNotFoundException extends RuntimeException {

    public AdventureNotFoundException(String message) {
        super(message);
    }
}
