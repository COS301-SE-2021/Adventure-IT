package com.adventureit.itinerary.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class AddItineraryEntryRequest {
    private RestTemplate restTemplate;
    private UUID entryContainerID;
    private String title;
    private String description;
    private UUID location;
    LocalDateTime timestamp;

    public AddItineraryEntryRequest(@JsonProperty("title") String title,@JsonProperty("description") String description,@JsonProperty("entryContainerID") UUID entryContainerID,@JsonProperty("location") String location,@JsonProperty("timestamp") LocalDateTime timestamp) {
        this.title = title;
        this.description = description;
        this.entryContainerID = entryContainerID;
        this.location = UUID.fromString(Objects.requireNonNull(restTemplate.getForObject("http://" + "localhost" + ":" + "9999" + "main/location/create/" + location, String.class)));
        this.timestamp = timestamp;
    }

    public UUID getEntryContainerID() {
        return entryContainerID;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getTimestamp() {
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

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
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
}
