package com.adventureit.budgetservice.Responses;

public class AddExpenseEntryResponse {
    boolean success;
    String message;

    public AddExpenseEntryResponse(boolean success){
        this.message = "Expense Entry added successfully!";
        this.success = success;
    }

    public AddExpenseEntryResponse(){}

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
