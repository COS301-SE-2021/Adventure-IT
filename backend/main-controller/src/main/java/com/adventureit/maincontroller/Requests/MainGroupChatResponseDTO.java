package com.adventureit.maincontroller.Requests;

import com.adventureit.chat.Entity.ColorPair;
import com.adventureit.userservice.Responses.GetUserByUUIDDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainGroupChatResponseDTO {
    UUID id;
    UUID adventureID;
    List<UUID> participants = new ArrayList<>();
    List<MessageResponseDTO> messages = new ArrayList<>();
    private String name;
    private List<ColorPair> colors;

    public MainGroupChatResponseDTO(UUID id, UUID adventureID, List<UUID> participants, List<MessageResponseDTO> messages, String name, List<ColorPair> colors) {
        this.id = id;
        this.adventureID = adventureID;
        this.participants = participants;
        this.messages = messages;
        this.name = name;
        this.colors = colors;
    }

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

    public List<MessageResponseDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageResponseDTO> messages) {
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ColorPair> getColors() {
        return colors;
    }

    public void setColors(List<ColorPair> colors) {
        this.colors = colors;
    }
}
