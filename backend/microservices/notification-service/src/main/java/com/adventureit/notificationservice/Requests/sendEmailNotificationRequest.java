package com.adventureit.notificationservice.Requests;

import java.util.UUID;

public class sendEmailNotificationRequest {
    private final UUID userId;
    private final String subject;
    private final String body;

    public sendEmailNotificationRequest(UUID userId, String subject, String body) {
        this.userId = userId;
        this.subject = subject;
        this.body = body;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
