package com.adventureit.chat.entity;


import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class DirectChat{

    @Id
    @NotNull
    UUID directChatId;
    @ElementCollection(fetch= FetchType.EAGER)
    List<UUID> participants = new ArrayList<>();


    public DirectChat(){}

    public DirectChat(UUID directChatId, UUID user1, UUID user2){
        List<UUID> x = new ArrayList<>(List.of(user1,user2));
        this.directChatId = directChatId;
        this.participants = x;
    }

    public DirectChat( UUID user1, UUID user2){
        List<UUID> x = new ArrayList<>(List.of(user1,user2));
        this.directChatId = UUID.randomUUID();
        this.participants = x;
    }

    public UUID getDirectChatId() {
        return directChatId;
    }

    public List<UUID> getParticipants() {
        return participants;
    }

}
