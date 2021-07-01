package com.adventureit.userservice.Exceptions;

public class InvalidUserPhoneNumberException extends RuntimeException {

    public InvalidUserPhoneNumberException(String message) {
        super(message);
    }
}
