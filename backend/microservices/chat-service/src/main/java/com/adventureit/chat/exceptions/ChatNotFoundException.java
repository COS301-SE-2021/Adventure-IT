package com.adventureit.chat.exceptions;

import java.util.UUID;

public class ChatNotFoundException extends RuntimeException {
    private final UUID chatID;
    private final boolean hasID;

    public ChatNotFoundException(UUID chatID) {
        this.chatID = chatID;
        this.hasID = true;
    }

    public ChatNotFoundException() {
        this.chatID = null;
        this.hasID = false;
    }

    @Override
    public String getMessage(){
        if(this.hasID && this.chatID != null){
            return "Chat with id "+this.chatID+" not found";
        }
        return "Chat not found";
    }
}
