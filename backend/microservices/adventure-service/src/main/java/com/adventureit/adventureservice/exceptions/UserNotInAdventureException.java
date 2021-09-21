package com.adventureit.adventureservice.exceptions;

public class UserNotInAdventureException extends RuntimeException {
    private final String message;
    public UserNotInAdventureException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }

}
