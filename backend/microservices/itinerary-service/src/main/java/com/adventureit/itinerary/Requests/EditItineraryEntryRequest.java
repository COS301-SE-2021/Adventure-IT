package com.adventureit.itinerary.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class EditItineraryEntryRequest {
    RestTemplate restTemplate;
    java.util.UUID id;
    UUID entryContainerID;
    String title;
    String description;
    UUID location;
    LocalDateTime timestamp;

    public EditItineraryEntryRequest(@JsonProperty("id") UUID id,@JsonProperty("entryContainerID") UUID entryContainerID,@JsonProperty("title") String title,@JsonProperty("description") String description,@JsonProperty("location") String location,@JsonProperty("timestamp") LocalDateTime timestamp){
        this.id = id;
        this.entryContainerID = entryContainerID;
        this.title = title;
        this.description = description;
        this.location = UUID.fromString(Objects.requireNonNull(restTemplate.getForObject("http://" + "localhost" + ":" + "9999" + "main/location/create/" + location, String.class)));
        this.timestamp = timestamp;
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

    public UUID getLocation() {
        return location;
    }

    public void setLocation(UUID location) {
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
