package com.adventureit.budgetservice.responses;

public class RemoveEntryResponse {
    private boolean success;
    private String message;

    public RemoveEntryResponse(boolean success){
        this.success = success;
        this.message = "Entry successfully removed.";
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
