package com.adventureit.budgetservice.Requests;

import java.util.UUID;

public class SoftDeleteRequest {
    private UUID id;

    public SoftDeleteRequest(){}

    public SoftDeleteRequest(UUID id){
        this.id = id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
