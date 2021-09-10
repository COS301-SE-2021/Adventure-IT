package com.adventureit.shareddtos.budget.responses;

public class AddUTOExpenseEntryResponse {
    boolean success;
    String message;

    public AddUTOExpenseEntryResponse(boolean success){
        this.message = "Entry added successfully!";
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }
}
