package com.adventureit.maincontroller.responses;

import com.adventureit.shareddtos.location.responses.LocationResponseDTO;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class MainItineraryEntryResponseDTO {
    private UUID id;
    private UUID entryContainerID;
    private String title;
    private String description;
    private boolean completed;
    private LocationResponseDTO location;
    LocalDateTime timestamp;
    private Map<UUID,Boolean> registeredUsers;

    public MainItineraryEntryResponseDTO(String title, String description, UUID id, UUID entryContainerID, boolean completed, LocationResponseDTO location, LocalDateTime timestamp, Map<UUID,Boolean> registeredUsers){
        this.title = title;
        this.description=description;
        this.entryContainerID =entryContainerID;
        this.id = id;
        this.completed = completed;
        this.location = location;
        this.timestamp = timestamp;
        this.registeredUsers = registeredUsers;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public UUID getEntryContainerID() {
        return entryContainerID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LocationResponseDTO getLocation() {
        return location;
    }

    public Map<UUID, Boolean> getRegisteredUsers() {
        return registeredUsers;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
