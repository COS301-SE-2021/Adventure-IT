package com.adventureit.chat.exceptions;

public class ChatNotFoundException extends RuntimeException {
    private final String message;

    public ChatNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
