package com.adventureit.shareddtos.budget.responses;

public class SoftDeleteResponse {
    boolean success;
    String message;

    public SoftDeleteResponse(boolean success){
        this.message = "Budget successfully moved to recycle bin!";
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
