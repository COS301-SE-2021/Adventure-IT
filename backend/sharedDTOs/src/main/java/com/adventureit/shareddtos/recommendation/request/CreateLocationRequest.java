package com.adventureit.shareddtos.recommendation.request;

import java.util.UUID;

public class CreateLocationRequest {
    public UUID locationId;
    public String locationString;

    public CreateLocationRequest(){};

    public CreateLocationRequest(UUID locationId, String locationString){
        this.locationId = locationId;
        this.locationString = locationString;
    }

    public UUID getLocationId() {
        return locationId;
    }

    public void setLocationId(UUID locationId)
    {
        this.locationId=locationId;
    }

    public String getLocationString() {
        return locationString;
    }

    public void setLocationString(String locationString) {
        this.locationString = locationString;
    }
}
