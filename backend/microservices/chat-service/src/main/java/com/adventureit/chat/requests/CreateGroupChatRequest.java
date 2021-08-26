package com.adventureit.chat.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public class CreateGroupChatRequest {
    private UUID adventureId;
    private List<UUID> participants;
    private String name;

    public CreateGroupChatRequest(@JsonProperty("adventureId") UUID adventureId, @JsonProperty("participants") List<UUID> participants, @JsonProperty("name") String name) {
        this.adventureId = adventureId;
        this.participants = participants;
        this.name = name;
    }

    public UUID getAdventureId() {
        return adventureId;
    }

    public List<UUID> getParticipants() {
        return participants;
    }

    public String getName() {
        return name;
    }
}
