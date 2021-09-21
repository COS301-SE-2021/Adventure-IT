package com.adventureit.shareddtos.chat.responses;

import com.adventureit.shareddtos.chat.MessageDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DirectChatResponseDTO {
    UUID id;
    List<UUID> participants = new ArrayList<>();
    List<MessageDTO> messages = new ArrayList<>();

    public DirectChatResponseDTO(UUID id, List<UUID> participants, List<MessageDTO> messages) {
        this.id = id;
        this.participants = participants;
        this.messages = messages;
    }

    public DirectChatResponseDTO(){}

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

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }
}
