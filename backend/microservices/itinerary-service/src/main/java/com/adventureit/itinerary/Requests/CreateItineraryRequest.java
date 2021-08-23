package com.adventureit.itinerary.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CreateItineraryRequest {
    String title;
    String description;
    UUID advID;
    UUID userID;

    public CreateItineraryRequest(@JsonProperty("title") String title, @JsonProperty("description") String description,@JsonProperty("advID") String advID,@JsonProperty("userID") String userID){
        this.title = title;
        this.description = description;
        this.advID = UUID.fromString(advID);
        this.userID = UUID.fromString(userID);
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public UUID getAdvID() {
        return advID;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAdvID(UUID advID) {
        this.advID = advID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }
}