package com.adventureit.recommendationservice.exception;

public class RecommendationException extends RuntimeException {
    private final String message;

    public RecommendationException(String s) {
        this.message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
