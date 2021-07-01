package com.adventureit.budgetservice.Responses;

public class RemoveEntryResponse {
    private boolean success;
    private String message;

    public RemoveEntryResponse(){}

    public RemoveEntryResponse(boolean success){
        this.success = success;
        this.message = "Income Entry successfully removed.";
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

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
