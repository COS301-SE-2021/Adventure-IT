package com.adventureit.shareddtos.recommendation.request;

import java.util.UUID;

public class CreateLocationRequest {
    public UUID locationId;

    public CreateLocationRequest(){};

    public CreateLocationRequest(UUID locationId){
        this.locationId = locationId;
    }

    public UUID getLocationId() {
        return locationId;
    }

    public void setLocationId(UUID locationId)
    {
        this.locationId=locationId;
    }
}
