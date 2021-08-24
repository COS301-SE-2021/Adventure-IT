package com.adventureit.timelineservice.exceptions;

public class TimelineDoesNotExistException extends RuntimeException{
    public TimelineDoesNotExistException(String message) {
            super(message);
        }

}
