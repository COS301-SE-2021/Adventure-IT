package com.adventureit.notificationservice.Responses;

public class SendEmailNotificationResponse {
    private final boolean success;
    private final String returnmessage;


    public SendEmailNotificationResponse(boolean success, String returnmessage) {
        this.success = success;
        this.returnmessage = returnmessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getReturnmessage() {
        return returnmessage;
    }
}
