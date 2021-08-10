package com.adventureit.chat.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Group chat limit has been reached")
public class GroupChatFullException extends RuntimeException {
    public GroupChatFullException(String message){
        super(message);
    }
}
