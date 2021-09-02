package com.adventureit.checklist.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class AddChecklistEntryRequest {
    private UUID entryContainerID;
    private String title;
    private UUID userId;

    public AddChecklistEntryRequest(@JsonProperty("entryContainerID") String entryContainerID, @JsonProperty("title") String title,@JsonProperty("userId") UUID userId){
        this.entryContainerID = UUID.fromString(entryContainerID);
        this.title = title;
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getEntryContainerID() {
        return entryContainerID;
    }

    public String getTitle() {
        return title;
    }

    public void setEntryContainerID(UUID entryContainerID) {
        this.entryContainerID = entryContainerID;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
