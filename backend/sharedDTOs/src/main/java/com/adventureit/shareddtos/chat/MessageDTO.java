package com.adventureit.shareddtos.chat;

import java.time.LocalDateTime;
import java.util.UUID;

public class MessageDTO {
    UUID id;
    UUID sender;
    UUID chatId;
    String payload;
    LocalDateTime timestamp;

    public MessageDTO(UUID id, UUID sender, UUID chatId, String message){
        this.id = id;
        this.sender = sender;
        this.payload = message;
        this.chatId= chatId;
        this.timestamp = LocalDateTime.now();
    }

    public MessageDTO(UUID id, UUID sender, UUID chatId, String message,LocalDateTime date){
        this.id = id;
        this.sender = sender;
        this.payload = message;
        this.chatId= chatId;
        this.timestamp = date;
    }

    public MessageDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setSender(UUID sender) {
        this.sender = sender;
    }

    public UUID getSender() {
        return this.sender;
    }

    public UUID getChatId(){return this.chatId;}

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


}
