package com.adventureit.budgetservice.responses;


public class AddUTUExpenseEntryResponse {
    boolean success;
    String message;

    public AddUTUExpenseEntryResponse(boolean success){
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
