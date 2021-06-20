package com.adventureit.notificationservice.Responses;

public class CreateNotificationResponse {
    private final String responseMessage;
    private final boolean success;

    public CreateNotificationResponse(String responseMessage, boolean success) {
        this.responseMessage = responseMessage;
        this.success = success;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public boolean isSuccess() {
        return success;
    }
}
