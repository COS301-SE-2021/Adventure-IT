package com.adventureit.shareddtos.chat;

import java.time.LocalDateTime;
import java.util.UUID;

public class DirectMessageDTO extends MessageDTO{

    public DirectMessageDTO(){}

    public DirectMessageDTO(UUID id, UUID sender, String message, LocalDateTime timestamp){
        this.id = id;
        this.sender = sender;
        this.payload = message;
        this.timestamp = timestamp;
    }

    public DirectMessageDTO(UUID id, UUID sender, UUID chatId, String message) {
        super(id, sender, chatId, message);

    }

    public DirectMessageDTO(UUID sender, String message){
        this.id = UUID.randomUUID();
        this.sender = sender;
        this.payload = message;
        this.timestamp = LocalDateTime.now();
    }

}
