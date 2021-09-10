package com.adventureit.shareddtos.chat.responses;

import com.adventureit.shareddtos.chat.ColorPairDTO;

import java.util.*;

public class GroupChatResponseDTO {
    UUID id;
    UUID adventureID;
    List<UUID> participants = new ArrayList<>();
    List<UUID> messages = new ArrayList<>();
    private String name;
    private List<ColorPairDTO> colors;

    public GroupChatResponseDTO(UUID id, UUID adventureID, List<UUID> participants, List<UUID> messages, String name,List<ColorPairDTO> colors){
        this.id = id;
        this.adventureID = adventureID;
        this.participants = participants;
        this.messages = messages;
        this.name = name;
        this.colors =colors;
    }

    public GroupChatResponseDTO(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAdventureID() {
        return adventureID;
    }

    public void setAdventureID(UUID adventureID) {
        this.adventureID = adventureID;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ColorPairDTO> getColors() {
        return colors;
    }

    public void setColors(List<ColorPairDTO> colors) {
        this.colors = colors;
    }
}
