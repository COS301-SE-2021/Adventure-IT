package com.adventureit.adventureservice.Requests;

import java.util.UUID;

public class GetAdventureByUUIDRequest {
    private UUID id;

    public GetAdventureByUUIDRequest(){}

    public GetAdventureByUUIDRequest(UUID id){
        this.id = id;

    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
