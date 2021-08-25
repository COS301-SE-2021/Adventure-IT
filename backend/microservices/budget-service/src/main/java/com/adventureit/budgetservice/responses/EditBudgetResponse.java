package com.adventureit.budgetservice.responses;

public class EditBudgetResponse {
    boolean success;
    String message;

    public EditBudgetResponse(boolean success){
        this.message = "Budget successfully edited!";
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
