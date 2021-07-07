package com.adventureit.notificationservice.Exceptions;

public class UserDoesNotExistException extends RuntimeException{
    public UserDoesNotExistException(String message){
        super(message);
    }
}
