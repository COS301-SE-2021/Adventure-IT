package com.adventureit.chat.Entity;


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
    UUID id;
    @ElementCollection(fetch= FetchType.EAGER)
    List<UUID> participants = new ArrayList<>();
    @ElementCollection
    List<UUID> messages = new ArrayList<>();

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
