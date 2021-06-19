package com.adventureit.userservice.Exceptions;

public class InvalidUserPasswordException extends RuntimeException{
    public InvalidUserPasswordException(String message) {
        super(message);
    }
}
