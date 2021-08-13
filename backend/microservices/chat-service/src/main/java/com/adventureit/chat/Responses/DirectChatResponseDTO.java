package com.adventureit.chat.Responses;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DirectChatResponseDTO {
    UUID id;
    UUID adventureID;
    List<UUID> participants = new ArrayList<>();
    List<UUID> messages = new ArrayList<>();

    public DirectChatResponseDTO(UUID id, UUID adventureID, List<UUID> participants, List<UUID> messages) {
        this.id = id;
        this.adventureID = adventureID;
        this.participants = participants;
        this.messages = messages;
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

    public List<UUID> getMessages() {
        return messages;
    }

    public void setMessages(List<UUID> messages) {
        this.messages = messages;
    }
}
