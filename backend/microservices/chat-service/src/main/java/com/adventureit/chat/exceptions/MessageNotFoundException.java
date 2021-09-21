package com.adventureit.chat.exceptions;

import java.util.UUID;

public class MessageNotFoundException extends RuntimeException {
    private final UUID messageId;

    public MessageNotFoundException(UUID id) {
        this.messageId = id;
    }

    @Override
    public String getMessage(){
        return "Message with id " + this.messageId + " not found";
    }
}
