package com.adventureit.userservice.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Invalid User phone number")
public class InvalidUserPhoneNumberException extends RuntimeException {

    public InvalidUserPhoneNumberException(String message) {
        super(message);
    }
}
