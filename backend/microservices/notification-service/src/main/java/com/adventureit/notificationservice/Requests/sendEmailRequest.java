package com.adventureit.notificationservice.Requests;

public class sendEmailRequest {
    private final String email;
    private final String subject;
    private final String body;

    public sendEmailRequest(String email, String subject, String body) {
        this.email = email;
        this.subject = subject;
        this.body = body;
    }

    public String getEmail() {
        return email;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
