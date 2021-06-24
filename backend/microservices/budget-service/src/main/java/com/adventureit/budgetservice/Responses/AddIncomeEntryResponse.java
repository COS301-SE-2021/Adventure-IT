package com.adventureit.budgetservice.Responses;

import java.util.UUID;

public class AddIncomeEntryResponse {
    boolean success;
    String message;

    public AddIncomeEntryResponse(boolean success){
        this.message = "Income Entry added successfully!";
        this.success = success;
    }

    public AddIncomeEntryResponse(){}

    public String getMessage() {
        return message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }
}
