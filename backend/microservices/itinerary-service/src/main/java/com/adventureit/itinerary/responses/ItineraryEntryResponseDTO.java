package com.adventureit.itinerary.responses;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ItineraryEntryResponseDTO {
    private UUID id;
    private UUID entryContainerID;
    private String title;
    private String description;
    private boolean completed;
    private UUID location;
    LocalDateTime timestamp;
    private List<UUID> usersPresent;

    public ItineraryEntryResponseDTO() {
    }

    public ItineraryEntryResponseDTO(UUID id, UUID entryContainerID, String title, String description, boolean completed, UUID location, LocalDateTime timestamp, List<UUID> usersPresent){
        this.title = title;
        this.description=description;
        this.entryContainerID =entryContainerID;
        this.id = id;
        this.completed = completed;
        this.location = location;
        this.timestamp = timestamp;
        this.usersPresent = usersPresent;
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

    public UUID getLocation() {
        return location;
    }

    public void setLocation(UUID location) {
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

    public List<UUID> getUsersPresent() {
        return usersPresent;
    }

    public void setUsersPresent(List<UUID> usersPresent) {
        this.usersPresent = usersPresent;
    }
}
