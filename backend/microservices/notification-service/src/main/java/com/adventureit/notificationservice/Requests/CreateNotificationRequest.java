package com.adventureit.notificationservice.Requests;

import java.util.UUID;

public class CreateNotificationRequest {
    private final UUID userId;
    private final String message;


    public CreateNotificationRequest(UUID userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }
}
