package com.adventureit.budgetservice.exception;

public class BudgetPermissionException extends RuntimeException {
    private final String message;
    public BudgetPermissionException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
