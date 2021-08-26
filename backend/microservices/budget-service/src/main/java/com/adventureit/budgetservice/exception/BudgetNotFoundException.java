package com.adventureit.budgetservice.exception;

public class BudgetNotFoundException extends RuntimeException {
    private final String message;

    public BudgetNotFoundException(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
