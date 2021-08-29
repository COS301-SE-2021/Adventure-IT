package com.adventureit.locationservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class CurrentLocation {
    @Id
    UUID id;
    UUID userID;
    String latitude;
    String longitude;
    LocalDateTime timestamp;

    public CurrentLocation(UUID id, UUID userID, String longitude, String latitude) {
        this.id = id;
        this.userID = userID;
        this.longitude = longitude;
        this.latitude = latitude;
        this.timestamp = LocalDateTime.now();
    }

    public CurrentLocation(UUID id, UUID userID, String longitude, String latitude, LocalDateTime timestamp) {
        this.id = id;
        this.userID = userID;
        this.longitude = longitude;
        this.latitude = latitude;
        this.timestamp = timestamp;
    }

    public CurrentLocation(UUID userID, String longitude, String latitude) {
        this.id = UUID.randomUUID();
        this.userID = userID;
        this.longitude = longitude;
        this.latitude = latitude;
        this.timestamp = LocalDateTime.now();
    }

    public CurrentLocation() {}

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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
