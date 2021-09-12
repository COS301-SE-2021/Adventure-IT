package com.adventureit.shareddtos.itinerary.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class RemoveItineraryEntryRequest {
    UUID id;
    UUID entryContainerID;

    public RemoveItineraryEntryRequest(@JsonProperty("id") UUID id, @JsonProperty("entryConainerID") UUID entryContainerID){
        this.id = id;
        this.entryContainerID = entryContainerID;
    }

    public UUID getEntryContainerID() {
        return entryContainerID;
    }

    public UUID getId() {
        return id;
    }

    public void setEntryContainerID(UUID entryContainerID) {
        this.entryContainerID = entryContainerID;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
