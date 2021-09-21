package com.adventureit.recommendationservice.exception;

import java.util.UUID;

public class LocationExistsException extends RuntimeException {
    private final String message;
    public LocationExistsException(UUID locationId) {
        this.message = "Location with UUID " + locationId.toString() + " already exists";
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
