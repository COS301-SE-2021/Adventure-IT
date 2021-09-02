package com.adventureit.itinerary.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

public class EditItineraryEntryRequest {
    RestTemplate restTemplate = new RestTemplate();
    UUID id;
    UUID userId;
    UUID entryContainerID;
    String title;
    String description;
    String location;
    UUID locationId;
    LocalDateTime timestamp;

    public EditItineraryEntryRequest(@JsonProperty("id") UUID id,@JsonProperty("entryContainerID") UUID entryContainerID,@JsonProperty("title") String title,@JsonProperty("description") String description,@JsonProperty("location") String location,@JsonProperty("timestamp") LocalDateTime timestamp,@JsonProperty("userId") UUID userId){
        this.id = id;
        this.entryContainerID = entryContainerID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.timestamp = timestamp;
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getTitle() {
        return title;
    }

    public UUID getId() {
        return id;
    }

    public UUID getEntryContainerID() {
        return entryContainerID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocationId(UUID locationId) {
        this.locationId = locationId;
    }

    public UUID getLocationId() {
        return locationId;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setEntryContainerID(UUID entryContainerID) {
        this.entryContainerID = entryContainerID;
    }
}
