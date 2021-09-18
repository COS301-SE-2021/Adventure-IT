package com.adventureit.shareddtos.notification.requests;

import java.util.UUID;

public class SendEmailNotificationRequest {
    private final UUID userId;
    private final String subject;
    private final String body;
    private final String email;

    public SendEmailNotificationRequest(UUID userId, String subject, String body, String email) {
        this.userId = userId;
        this.subject = subject;
        this.body = body;
        this.email = email;
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

    public String getEmail() {
        return email;
    }
}
