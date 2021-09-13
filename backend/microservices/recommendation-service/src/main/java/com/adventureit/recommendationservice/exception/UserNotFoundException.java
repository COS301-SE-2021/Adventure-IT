package com.adventureit.recommendationservice.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    private final String message;

    public UserNotFoundException(UUID id){
        this.message = "User with UUID: " + id.toString() + " not found";
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
