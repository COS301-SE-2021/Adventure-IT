package com.adventureit.timelineservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Timeline {
    @Id
    private UUID timelineId;
    private UUID adventureId;
    private String description;
    private LocalDateTime timestamp;
    private TimelineType type;



    public Timeline(UUID timelineId, UUID adventureId, String description, LocalDateTime timestamp, TimelineType type) {
        this.timelineId = timelineId;
        this.adventureId = adventureId;


        this.description = description;
        this.timestamp = timestamp;
        this.type = type;
    }

    public Timeline(){

    }

    public UUID getTimelineId() {
        return timelineId;
    }

    public UUID getAdventureId() {
        return adventureId;
    }

    public void setAdventureId(UUID adventureID) {
        this.adventureId = adventureID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public TimelineType getType() {
        return type;
    }

    public void setType(TimelineType type) {
        this.type = type;
    }
}
