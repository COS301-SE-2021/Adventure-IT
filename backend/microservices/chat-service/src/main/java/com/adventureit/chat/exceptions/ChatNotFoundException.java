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
        if(this.hasID){
            return "Chat with id "+this.chatID.toString()+" not found";
        }
        return "Chat not found";
    }
}
