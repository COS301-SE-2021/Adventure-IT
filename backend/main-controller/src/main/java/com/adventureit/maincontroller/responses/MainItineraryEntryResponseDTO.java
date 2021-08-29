package com.adventureit.maincontroller.responses;

import com.adventureit.locationservice.responses.LocationResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class MainItineraryEntryResponseDTO {
    private UUID id;
    private UUID entryContainerID;
    private String title;
    private String description;
    private boolean completed;
    private LocationResponseDTO location;
    LocalDateTime timestamp;
    private List<UUID> usersPresent;

    public MainItineraryEntryResponseDTO(String title, String description, UUID id, UUID entryContainerID, boolean completed, LocationResponseDTO location, LocalDateTime timestamp, List<UUID> usersPresent){
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

    public List<UUID> getUsersPresent() {
        return usersPresent;
    }

    public void setUsersPresent(List<UUID> usersPresent) {
        this.usersPresent = usersPresent;
    }
}
