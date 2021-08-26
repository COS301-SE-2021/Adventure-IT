package com.adventureit.checklist.exceptions;

public class UnauthorisedException extends RuntimeException{
    public UnauthorisedException(String message){
        super(message);
    }
}
