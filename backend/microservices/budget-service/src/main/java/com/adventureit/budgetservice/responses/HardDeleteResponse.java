package com.adventureit.budgetservice.responses;

public class HardDeleteResponse {
    boolean success;
    String message;

    public HardDeleteResponse(boolean success){
        this.message = "Budget permanently deleted!";
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
