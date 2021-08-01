package com.adventureit.timelineservice.Requests;

import com.adventureit.timelineservice.Entity.TimelineType;

import java.util.UUID;

public class CreateTimelineRequest {

    final private UUID adventureID;
    final private UUID userID;
    final private TimelineType type;
    final private String description;

    public CreateTimelineRequest(UUID adventureID, UUID userID, TimelineType type, String description) {
        this.adventureID = adventureID;
        this.userID = userID;
        this.type = type;
        this.description = description;
    }

    public UUID getAdventureID() {
        return adventureID;
    }

    public UUID getUserID() {
        return userID;
    }

    public TimelineType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
