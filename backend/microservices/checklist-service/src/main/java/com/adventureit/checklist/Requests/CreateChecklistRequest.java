package com.adventureit.checklist.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CreateChecklistRequest {
    String title;
    String description;
    UUID id;
    UUID advID;
    UUID userID;

    public CreateChecklistRequest(@JsonProperty("title") String title, @JsonProperty("description") String description, @JsonProperty("id") String id, @JsonProperty("advID") String advID, @JsonProperty("userID") String userID){
        this.title = title;
        this.description = description;
        this.id = UUID.fromString(id);
        this.advID = UUID.fromString(advID);
        this.userID = UUID.fromString(userID);
    }

    public UUID getId() {
        return id;
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

    public void setId(UUID id) {
        this.id = id;
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