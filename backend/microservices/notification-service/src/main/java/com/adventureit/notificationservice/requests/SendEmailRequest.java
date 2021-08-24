package com.adventureit.notificationservice.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SendEmailRequest {
    private final String email;
    private final String subject;
    private final String body;

    public SendEmailRequest(@JsonProperty("email")String email, @JsonProperty("subject") String subject, @JsonProperty("body") String body) {
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
