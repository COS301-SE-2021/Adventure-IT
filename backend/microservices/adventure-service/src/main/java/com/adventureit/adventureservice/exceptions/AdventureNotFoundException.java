package com.adventureit.adventureservice.exceptions;

public class AdventureNotFoundException extends RuntimeException {

    public AdventureNotFoundException(String message) {
        super(message);
    }
}
