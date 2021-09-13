package com.adventureit.shareddtos.chat;

import java.time.LocalDateTime;
import java.util.UUID;

public class DirectMessageDTO extends MessageDTO{
    Boolean read = false;
    UUID receiver;

    public DirectMessageDTO(){}

    public DirectMessageDTO(UUID id, UUID sender, UUID receiver, String message, LocalDateTime timestamp){
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.payload = message;
        this.timestamp = timestamp;
    }

    public DirectMessageDTO(UUID id, UUID sender, UUID chatId, String message, Boolean read, UUID receiver) {
        super(id, sender, chatId, message);
        this.read = read;
        this.receiver = receiver;
    }


    public DirectMessageDTO(UUID sender, UUID receiver, String message){
        this.id = UUID.randomUUID();
        this.sender = sender;
        this.receiver = receiver;
        this.payload = message;
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
