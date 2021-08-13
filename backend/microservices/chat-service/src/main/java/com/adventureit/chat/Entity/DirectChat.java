package com.adventureit.chat.Entity;


import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class DirectChat extends Chat{

    public DirectChat(){}

    public DirectChat(UUID id, UUID user1, UUID user2){
        List<UUID> participants = new ArrayList<>(List.of(user1,user2));
        this.id = id;
        this.participants = participants;
    }

    public DirectChat( UUID user1, UUID user2){
        List<UUID> participants = new ArrayList<>(List.of(user1,user2));
        this.id = UUID.randomUUID();
        this.participants = participants;
    }


}
