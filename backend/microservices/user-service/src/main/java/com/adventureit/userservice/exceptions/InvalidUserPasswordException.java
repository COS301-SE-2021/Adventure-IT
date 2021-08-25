package com.adventureit.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Invalid User password")
public class InvalidUserPasswordException extends RuntimeException{
    public InvalidUserPasswordException(String message) {
        super(message);
    }
}
