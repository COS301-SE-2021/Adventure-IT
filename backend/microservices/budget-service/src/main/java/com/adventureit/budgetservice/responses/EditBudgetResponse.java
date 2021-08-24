package com.adventureit.budgetservice.responses;

public class EditBudgetResponse {
    boolean success;
    String message;

    public EditBudgetResponse(boolean success){
        this.message = "Budget successfully edited!";
        this.success = success;
    }

    public EditBudgetResponse(){}

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
