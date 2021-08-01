package com.adventureit.itinerary.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public class AddItineraryEntryRequest {
    private UUID id;
    private UUID entryContainerID;
    private String title;
    private String description;
    private String location;
    LocalDateTime timestamp;

    public AddItineraryEntryRequest(@JsonProperty("title") String title,@JsonProperty("description") String description,@JsonProperty("id") UUID id,@JsonProperty("entryContainerID") UUID entryContainerID,@JsonProperty("location") String location,@JsonProperty("timestamp") LocalDateTime timestamp) {
        this.title = title;
        this.description = description;
        this.entryContainerID = entryContainerID;
        this.id = id;
        this.location = location;
        this.timestamp = timestamp;
    }

    public UUID getEntryContainerID() {
        return entryContainerID;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public void setEntryContainerID(UUID entryContainerID) {
        this.entryContainerID = entryContainerID;
    }

    public void setId(UUID id) {
        this.id = id;
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
}
