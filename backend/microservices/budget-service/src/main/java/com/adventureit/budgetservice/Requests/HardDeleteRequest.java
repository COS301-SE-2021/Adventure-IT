package com.adventureit.budgetservice.Requests;

import java.util.UUID;

public class HardDeleteRequest {
    private UUID id;

    HardDeleteRequest(){}

    HardDeleteRequest(UUID id){
        this.id = id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
