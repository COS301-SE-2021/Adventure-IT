package com.adventureit.budgetservice.Responses;

public class RemoveExpenseEntryResponse {
    private boolean success;
    private String message;

    public RemoveExpenseEntryResponse(){}

    public RemoveExpenseEntryResponse(boolean success){
        this.success = success;
        this.message = "Expense Entry successfully removed.";
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
