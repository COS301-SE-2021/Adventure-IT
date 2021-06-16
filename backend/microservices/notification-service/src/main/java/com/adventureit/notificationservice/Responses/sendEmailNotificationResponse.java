package com.adventureit.notificationservice.Responses;

public class sendEmailNotificationResponse {
    private final boolean success;
    private final String returnmessage;


    public sendEmailNotificationResponse(boolean success, String returnmessage) {
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
