package com.adventureit.recommendationservice.exception;

import java.util.UUID;

public class UserExistsException extends RuntimeException {
    private final String message;
    public UserExistsException(UUID userId) {
        this.message = "User with UUID " + userId.toString() + " already exists";
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
