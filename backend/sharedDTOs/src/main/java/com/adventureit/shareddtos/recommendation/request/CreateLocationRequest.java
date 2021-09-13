package com.adventureit.shareddtos.recommendation.request;

import java.util.UUID;

public class CreateLocationRequest {
    public final UUID locationId;

    public CreateLocationRequest(UUID id){
        this.locationId = id;
    }
}
