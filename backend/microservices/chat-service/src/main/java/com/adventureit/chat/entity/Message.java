package com.adventureit.chat.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Message {
    @Id
    UUID id;
    //@NotNull
    UUID sender;
    //@NotNull
    UUID chatId;
    //@NotNull
    String message;
    //@NotNull
    LocalDateTime timestamp;

    public Message(UUID id, UUID sender,UUID chatId, String message){
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.chatId= chatId;
        this.timestamp = LocalDateTime.now();
    }

    public Message() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
