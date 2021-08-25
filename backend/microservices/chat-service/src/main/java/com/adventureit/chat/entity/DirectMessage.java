package com.adventureit.chat.entity;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class DirectMessage extends Message{
    Boolean read = false;
    UUID receiver;

    public DirectMessage(){}

    public DirectMessage(UUID id, UUID sender, UUID receiver, String message, LocalDateTime timestamp){
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.timestamp = timestamp;
    }

    public DirectMessage(UUID id, UUID sender, UUID chatId, String message, Boolean read, UUID receiver) {
        super(id, sender, chatId, message);
        this.read = read;
        this.receiver = receiver;
    }


    public DirectMessage(UUID sender, UUID receiver, String message){
        this.id = UUID.randomUUID();
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public UUID getReceiver() {
        return receiver;
    }

    public void setReceiver(UUID receiver) {
        this.receiver = receiver;
    }
}
