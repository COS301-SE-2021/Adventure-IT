package com.adventureit.budgetservice.requests;

import java.util.UUID;

public class ViewBudgetRequest {
    UUID id;

    public ViewBudgetRequest(){}

    public ViewBudgetRequest(UUID id){
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
