package com.adventureit.userservice.Exceptions;

public class InvalidUserEmailException extends RuntimeException {

    public InvalidUserEmailException(String message){
        super(message);
    }

}
