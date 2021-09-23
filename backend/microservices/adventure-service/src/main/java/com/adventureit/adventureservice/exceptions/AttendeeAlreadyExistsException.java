package com.adventureit.adventureservice.exceptions;

/**
 * The AttendeeAlreadyExistsException
 * This exception is is thrown when an attendee has already been added to the adventure and a user tries to add them again
 */
public class AttendeeAlreadyExistsException extends RuntimeException{
    public AttendeeAlreadyExistsException(String message){
        super(message);
    }
}
