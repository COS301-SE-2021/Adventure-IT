package com.adventureit.maincontroller.Responses;

import com.adventureit.locationservice.responses.LocationResponseDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class MainItineraryEntryResponseDTO {
    private UUID id;
    private UUID entryContainerID;
    private String title;
    private String description;
    private boolean completed;
    private LocationResponseDTO location;
    LocalDateTime timestamp;

    public MainItineraryEntryResponseDTO(String title, String description, UUID id, UUID entryContainerID, boolean completed, LocationResponseDTO location, LocalDateTime timestamp){
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

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getEntryContainerID() {
        return entryContainerID;
    }

    public void setEntryContainerID(UUID entryContainerID) {
        this.entryContainerID = entryContainerID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocationResponseDTO getLocation() {
        return location;
    }

    public void setLocation(LocationResponseDTO location) {
        this.location = location;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
