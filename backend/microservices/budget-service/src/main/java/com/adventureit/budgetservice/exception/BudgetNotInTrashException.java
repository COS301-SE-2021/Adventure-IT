package com.adventureit.budgetservice.exception;

public class BudgetNotInTrashException extends RuntimeException {
    private final String message;
    public BudgetNotInTrashException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
