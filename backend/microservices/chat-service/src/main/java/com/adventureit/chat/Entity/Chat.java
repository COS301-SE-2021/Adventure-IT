package com.adventureit.chat.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Chat {
    @Id
    @NotNull
    UUID id;
    @ElementCollection (fetch=FetchType.EAGER)
    List<UUID> participants = new ArrayList<>();
    @ElementCollection
    List<UUID> messages = new ArrayList<>();



    public Chat(){}

    public Chat(UUID id,List<UUID> participants){
        this.id = id;
        this.participants = participants;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<UUID> getParticipants() {
        return participants;
    }

    public void setParticipants(List<UUID> participants) {
        this.participants = participants;
    }

    public List<UUID> getMessages() {
        return messages;
    }

    public void setMessages(List<UUID> messages) {
        this.messages = messages;
    }

    public int getColor(UUID userID){ return 1; }

}
