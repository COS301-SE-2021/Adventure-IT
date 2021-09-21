package com.adventureit.maincontroller.exceptions;

public class InvalidItineraryEntryException extends RuntimeException{
    public InvalidItineraryEntryException(String message){
        super(message);
    }
}
