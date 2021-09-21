package com.adventureit.budgetservice.exception;

public class MalformedBudgetRequestException extends RuntimeException {
    private final String message;
    public MalformedBudgetRequestException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
