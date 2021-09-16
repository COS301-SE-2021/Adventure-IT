package com.adventureit.chat.entity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GroupMessage extends Message{
    Map<UUID, Boolean> read = new HashMap<>();

    public GroupMessage(){}

    public GroupMessage(UUID id, UUID sender, String message){
        this.id = id;
        this.sender = sender;
        this.payload = message;
        this.timestamp = LocalDateTime.now();


    }

    public GroupMessage(UUID id, UUID sender, UUID chatId, String message) {
        super(id, sender, chatId, message);

    }



    public GroupMessage(UUID sender, List<UUID> receivers, String message){
        this.id = UUID.randomUUID();
        this.sender = sender;
        this.payload = message;
        this.timestamp = LocalDateTime.now();

        for (UUID ID: receivers) {
            read.put(ID,false);
        }
    }

    public Map<UUID, Boolean> getRead() {
        return read;
    }

    public void setRead(Map<UUID, Boolean> read) {
        this.read = read;
    }

}
