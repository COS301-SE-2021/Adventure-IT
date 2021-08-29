package com.adventureit.locationservice.responses;

import java.time.LocalDateTime;
import java.util.UUID;

public class CurrentLocationResponseDTO {
    UUID id;
    UUID userID;
    String latitude;
    String longitude;
    LocalDateTime timestamp;

    public CurrentLocationResponseDTO(UUID id, UUID userID, String latitude, String longitude, LocalDateTime timestamp) {
        this.id = id;
        this.userID = userID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
