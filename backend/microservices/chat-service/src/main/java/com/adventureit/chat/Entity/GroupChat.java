package com.adventureit.chat.Entity;

import javax.persistence.Entity;
import java.util.List;
import java.util.UUID;

@Entity
public class GroupChat extends Chat {

    private String name;

    public GroupChat(){}

    public GroupChat(UUID id, UUID adventureID, List<UUID> participants, String name){
        super(id,adventureID,participants);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
