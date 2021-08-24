package com.adventureit.timelineservice.responses;

import com.adventureit.timelineservice.entity.TimelineType;

import java.time.LocalDateTime;
import java.util.UUID;

public class TimelineDTO {
    private final UUID timelineId;
    private final UUID adventureId;
    private final String description;
    private final LocalDateTime timestamp;
    private final TimelineType type;

    public TimelineDTO(UUID timelineId, UUID adventureID, String description, LocalDateTime timestamp, TimelineType type) {
        this.timelineId = timelineId;
        this.adventureId = adventureID;
        this.description = description;
        this.timestamp = timestamp;
        this.type = type;
    }

    public UUID getTimelineId() {
        return timelineId;
    }

    public UUID getAdventureId() {
        return adventureId;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public TimelineType getType() {
        return type;
    }
}
