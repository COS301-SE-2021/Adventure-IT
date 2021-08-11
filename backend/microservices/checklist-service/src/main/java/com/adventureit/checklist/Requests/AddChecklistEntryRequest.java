package com.adventureit.checklist.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class AddChecklistEntryRequest {
    private UUID entryContainerID;
    private String title;

    public AddChecklistEntryRequest(@JsonProperty("entryContainerID") String entryContainerID, @JsonProperty("title") String title){
        this.entryContainerID = UUID.fromString(entryContainerID);
        this.title = title;
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
