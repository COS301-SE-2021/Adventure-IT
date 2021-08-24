package com.adventureit.budgetservice.responses;

public class CreateBudgetResponse {
    private boolean success;
    private String message;

    public CreateBudgetResponse(){}

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

    public void setSuccess(boolean success){
        this.success = success;
    }

    public boolean isSuccess(){
        return this.success;
    }
}
