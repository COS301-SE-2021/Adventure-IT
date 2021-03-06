package com.adventureit.shareddtos.adventure.requests;

import java.util.UUID;

public class GetAdventureByUUIDRequest{
    private UUID id;

    /**
     * This service will be used to generate a GetAdventureByUUID request
     * @param id id of the adventure that needs to be retrieved
     */

    public GetAdventureByUUIDRequest(UUID id){
        this.id = id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public UUID getId(){
        return id;
    }
}
