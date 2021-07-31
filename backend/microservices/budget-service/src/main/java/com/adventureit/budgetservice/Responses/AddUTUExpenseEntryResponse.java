package com.adventureit.budgetservice.Responses;

import java.util.UUID;

public class AddUTUExpenseEntryResponse {
    boolean success;
    String message;

    public AddUTUExpenseEntryResponse(boolean success){
        this.message = "Entry added successfully!";
        this.success = success;
    }

    public AddUTUExpenseEntryResponse(){}

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
