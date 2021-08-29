package com.adventureit.maincontroller.exceptions;

public class CurrentLocationException extends RuntimeException{
    public CurrentLocationException(String message){
        super(message);
    }
}
