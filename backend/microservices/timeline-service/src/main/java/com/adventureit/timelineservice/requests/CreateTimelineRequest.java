package com.adventureit.timelineservice.requests;

import com.adventureit.timelineservice.entity.TimelineType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CreateTimelineRequest {

    private final UUID adventureId;
    private final TimelineType type;
    private final String description;

    public CreateTimelineRequest(@JsonProperty("adventureID") UUID adventureID,@JsonProperty("type") TimelineType type,@JsonProperty("description") String description) {
        this.adventureId = adventureID;
        this.type = type;
        this.description = description;
    }

    public UUID getAdventureID() {
        return adventureId;
    }

    public TimelineType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
