package com.adventureit.timelineservice.Responses;

import com.adventureit.timelineservice.Entity.TimelineType;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

public class TimelineDTO {
    final private UUID timelineID;
    final private UUID adventureID;
    final private UUID userID;
    final private String descrpition;
    final private LocalDateTime timestamp;
    final private TimelineType type;

    public TimelineDTO(UUID timelineID, UUID adventureID, UUID userID, String descrpition, LocalDateTime timestamp, TimelineType type) {
        this.timelineID = timelineID;
        this.adventureID = adventureID;
        this.userID = userID;
        this.descrpition = descrpition;
        this.timestamp = timestamp;
        this.type = type;
    }

    public UUID getTimelineID() {
        return timelineID;
    }

    public UUID getAdventureID() {
        return adventureID;
    }

    public UUID getUserID() {
        return userID;
    }

    public String getDescrpition() {
        return descrpition;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public TimelineType getType() {
        return type;
    }
}
