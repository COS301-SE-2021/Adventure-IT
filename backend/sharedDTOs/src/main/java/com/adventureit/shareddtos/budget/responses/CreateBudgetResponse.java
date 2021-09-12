package com.adventureit.shareddtos.budget.responses;

public class CreateBudgetResponse {
    private final boolean success;
    private String message;

    public CreateBudgetResponse(boolean success){
        this.success = true;
        this.message = "Budget was successfully created!";
    }

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public boolean isSuccess(){
        return this.success;
    }
}
