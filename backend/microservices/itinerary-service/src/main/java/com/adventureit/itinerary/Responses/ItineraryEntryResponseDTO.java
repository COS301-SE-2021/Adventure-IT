package com.adventureit.itinerary.Responses;

import java.time.LocalDateTime;
import java.util.UUID;

public class ItineraryEntryResponseDTO {
    private UUID id;
    private UUID entryContainerID;
    private String title;
    private String description;
    private boolean completed;
    private String location;
    LocalDateTime timestamp;

    public ItineraryEntryResponseDTO(UUID id, UUID entryContainerID, String title, String description, boolean completed, String location, LocalDateTime timestamp){
        this.title = title;
        this.description=description;
        this.entryContainerID =entryContainerID;
        this.id = id;
        this.completed = completed;
        this.location = location;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }

    public UUID getEntryContainerID() {
        return entryContainerID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getLocation() {
        return location;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setEntryContainerID(UUID entryContainerID) {
        this.entryContainerID = entryContainerID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed;
    }
}
