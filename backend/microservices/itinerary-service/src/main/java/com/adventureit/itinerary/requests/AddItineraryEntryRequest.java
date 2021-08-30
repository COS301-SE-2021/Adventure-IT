package com.adventureit.itinerary.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class AddItineraryEntryRequest {
    private UUID entryContainerID;
    private UUID userId;
    private String title;
    private String description;
    private String location;
    String timestamp;

    public AddItineraryEntryRequest(@JsonProperty("title") String title,@JsonProperty("description") String description,@JsonProperty("entryContainerID") UUID entryContainerID,@JsonProperty("location") String location,@JsonProperty("timestamp") String timestamp,@JsonProperty("userId") UUID userId) {
        this.title = title;
        this.description = description;
        this.entryContainerID = entryContainerID;
        this.location = location;
        this.timestamp = timestamp;
        this.userId = userId;
    }

    public UUID getEntryContainerID() {
        return entryContainerID;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setEntryContainerID(UUID entryContainerID) {
        this.entryContainerID = entryContainerID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
