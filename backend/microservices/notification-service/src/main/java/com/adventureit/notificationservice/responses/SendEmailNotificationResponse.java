package com.adventureit.notificationservice.responses;

public class SendEmailNotificationResponse {
    private final boolean success;
    private final String returnMessage;


    public SendEmailNotificationResponse(boolean success, String returnMessage) {
        this.success = success;
        this.returnMessage = returnMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getReturnMessage() {
        return returnMessage;
    }
}
