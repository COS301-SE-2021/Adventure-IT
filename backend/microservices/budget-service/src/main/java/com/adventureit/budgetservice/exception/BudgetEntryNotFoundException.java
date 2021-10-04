package com.adventureit.budgetservice.exception;

/**
 * BudgetEntryNotFoundException
 * This exception is is thrown when a budget entry cannot be found in the database
 */
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
