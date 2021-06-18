package com.adventureit.budgetservice.Responses;

public class SoftDeleteResponse {
    boolean success;
    String message;

    public SoftDeleteResponse(boolean success){
        this.message = "Budget successfully moved to recycle bin!";
        this.success = success;
    }

    public SoftDeleteResponse(){}

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
