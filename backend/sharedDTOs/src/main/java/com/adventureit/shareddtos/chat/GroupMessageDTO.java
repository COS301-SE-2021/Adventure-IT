package com.adventureit.shareddtos.chat;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GroupMessageDTO extends MessageDTO{

    public GroupMessageDTO(){}

    public GroupMessageDTO(UUID id, UUID sender, String message){
        this.id = id;
        this.sender = sender;
        this.payload = message;
        this.timestamp = LocalDateTime.now();


    }

    public GroupMessageDTO(UUID id, UUID sender, UUID chatId, String message) {
        super(id, sender, chatId, message);

    }



    public GroupMessageDTO(UUID sender, List<UUID> receivers, String message){
        this.id = UUID.randomUUID();
        this.sender = sender;
        this.payload = message;
        this.timestamp = LocalDateTime.now();

    }


}
