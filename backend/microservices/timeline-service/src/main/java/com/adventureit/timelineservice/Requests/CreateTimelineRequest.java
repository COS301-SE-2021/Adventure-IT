package com.adventureit.timelineservice.Requests;

import com.adventureit.timelineservice.Entity.TimelineType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CreateTimelineRequest {

    final private UUID adventureId;
    final private TimelineType type;
    final private String description;

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
