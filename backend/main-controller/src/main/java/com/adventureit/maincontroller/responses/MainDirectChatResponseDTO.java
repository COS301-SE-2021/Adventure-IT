package com.adventureit.maincontroller.responses;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainDirectChatResponseDTO {
    UUID id;
    List<UUID> participants = new ArrayList<>();

    public MainDirectChatResponseDTO(UUID id, List<UUID> participants) {
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
}
