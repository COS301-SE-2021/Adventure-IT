package com.adventureit.checklist.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class MarkChecklistEntryRequest {
    UUID id;
    UUID entryContainerID;

    public MarkChecklistEntryRequest(@JsonProperty("id")UUID id, @JsonProperty("entryContainerID") UUID entryContainerID){
        this.id = id;
        this.entryContainerID = entryContainerID;
    }

    public UUID getId() {
        return id;
    }

    public UUID getEntryContainerID() {
        return entryContainerID;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setEntryContainerID(UUID entryContainerID) {
        this.entryContainerID = entryContainerID;
    }
}
