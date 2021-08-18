package com.adventureit.timelineservice.Responses;

import com.adventureit.timelineservice.Entity.TimelineType;

import java.time.LocalDateTime;
import java.util.UUID;

public class TimelineDTO {
    final private UUID timelineId;
    final private UUID adventureId;
    final private String description;
    final private LocalDateTime timestamp;
    final private TimelineType type;

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
