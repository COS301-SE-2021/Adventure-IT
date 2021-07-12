package com.adventureit.chat.Entity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
public class GroupMessage extends Message{
    @ElementCollection
    List<UUID> receivers;
    @ElementCollection (fetch = FetchType.EAGER)
    Map<UUID, Boolean> read = new HashMap<>();

    public GroupMessage(){}

    public GroupMessage(UUID id, UUID sender, List<UUID> receivers, String message){
        this.id = id;
        this.sender = sender;
        this.receivers = receivers;
        this.message = message;
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

    public List<UUID> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<UUID> receivers) {
        this.receivers = receivers;
    }
}
