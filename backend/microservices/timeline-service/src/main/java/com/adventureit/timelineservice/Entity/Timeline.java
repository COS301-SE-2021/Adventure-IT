package com.adventureit.timelineservice.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Timeline {
    @Id
    private UUID timelineID;
    private UUID adventureID;
    private UUID userID;
    private String descrpition;
    private LocalDateTime timestamp;
    private TimelineType type;


    public Timeline(UUID timelineID, UUID adventureID, UUID userID, String descrpition, LocalDateTime timestamp, TimelineType type) {
        this.timelineID = timelineID;
        this.adventureID = adventureID;
        this.userID = userID;
        this.descrpition = descrpition;
        this.timestamp = timestamp;
        this.type = type;
    }

    public Timeline(){

    }

    public UUID getTimelineID() {
        return timelineID;
    }

    public void setTimelineID(UUID timelineID) {
        this.timelineID = timelineID;
    }

    public UUID getAdventureID() {
        return adventureID;
    }

    public void setAdventureID(UUID adventureID) {
        this.adventureID = adventureID;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public String getDescrpition() {
        return descrpition;
    }

    public void setDescrpition(String descrpition) {
        this.descrpition = descrpition;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public TimelineType getType() {
        return type;
    }

    public void setType(TimelineType type) {
        this.type = type;
    }
}
