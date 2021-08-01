package com.adventureit.checklist.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class AddChecklistEntryRequest {
    private UUID id;
    private UUID entryContainerID;
    private String title;

    public AddChecklistEntryRequest(@JsonProperty("id") UUID id, @JsonProperty("entryContainerID") UUID entryContainerID, @JsonProperty("title") String title){
        this.id = id;
        this.entryContainerID = entryContainerID;
        this.title = title;
    }

    public UUID getId() {
        return id;
    }

    public UUID getEntryContainerID() {
        return entryContainerID;
    }

    public String getTitle() {
        return title;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setEntryContainerID(UUID entryContainerID) {
        this.entryContainerID = entryContainerID;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
