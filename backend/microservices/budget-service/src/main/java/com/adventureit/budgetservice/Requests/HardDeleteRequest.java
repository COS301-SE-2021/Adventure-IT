package com.adventureit.budgetservice.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class HardDeleteRequest {
    private UUID id;

    public HardDeleteRequest(){}

    public HardDeleteRequest(@JsonProperty("id") String id){
        this.id = UUID.fromString(id);
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
