package com.adventureit.timelineservice.Exceptions;

public class TimelineDoesNotExistException extends RuntimeException{
    public TimelineDoesNotExistException(String message) {
            super(message);
        }

}
