package com.adventureit.itinerary.exceptions;

public class UnauthorisedException extends RuntimeException{
    public UnauthorisedException(String message){
        super(message);
    }
}
