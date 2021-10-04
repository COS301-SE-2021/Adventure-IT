package com.adventureit.adventureservice.exceptions;

/**
 * The UserNotInAdventureException
 * This exception is thrown when a user has does not belong to an adventure
 */
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
