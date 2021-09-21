package com.adventureit.budgetservice.exception;

public class BudgetEntryNotFoundException extends RuntimeException {
    private final String message;
    public BudgetEntryNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
