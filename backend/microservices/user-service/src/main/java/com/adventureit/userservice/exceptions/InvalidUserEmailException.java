package com.adventureit.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Invalid User email")
public class InvalidUserEmailException extends RuntimeException {
    public InvalidUserEmailException(String message){
        super(message);
    }
}
