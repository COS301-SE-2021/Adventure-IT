package com.adventureit.budgetservice.Responses;

public class RemoveIncomeEntryResponse {
    private boolean success;
    private String message;

    public RemoveIncomeEntryResponse(){}

    public RemoveIncomeEntryResponse(boolean success){
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
