package com.adventureit.shareddtos.checklist.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class EditChecklistEntryRequest {
    UUID id;
    UUID entryContainerID;
    UUID userId;
    String title;

    public EditChecklistEntryRequest(@JsonProperty("id") UUID id,@JsonProperty("entryContainerID") UUID entryContainerID,@JsonProperty("userId") UUID userId, @JsonProperty("title") String title){
        this.id = id;
        this.entryContainerID = entryContainerID;
        this.title = title;
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public UUID getEntryContainerID() {
        return entryContainerID;
    }

    public UUID getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEntryContainerID(UUID entryContainerID) {
        this.entryContainerID = entryContainerID;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
