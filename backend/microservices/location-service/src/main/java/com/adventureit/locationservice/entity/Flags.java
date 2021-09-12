package com.adventureit.locationservice.entity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
public class Flags {
    @Id
    UUID userID;
    @ElementCollection
    List<String> placesVisited;
    @ElementCollection
    Map<String,String> flagMap;

    public Flags(UUID userID, List<String> placesVisited) {
        this.userID = userID;
        this.placesVisited = placesVisited;
    }

    public Flags() {

    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public List<String> getPlacesVisited() {
        return placesVisited;
    }

    public void setPlacesVisited(List<String> placesVisited) {
        this.placesVisited = placesVisited;
    }

    public Map<String, String> getFlagMap() {
        return flagMap;
    }

    public void setFlagMap(Map<String, String> flagMap) {
        this.flagMap = flagMap;
    }
}
