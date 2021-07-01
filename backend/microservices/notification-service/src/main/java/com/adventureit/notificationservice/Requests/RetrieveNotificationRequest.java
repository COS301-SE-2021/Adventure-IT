package com.adventureit.notificationservice.Requests;



import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class RetrieveNotificationRequest {
    private String userId_S = "";
    private UUID userId_U = null;
    private final boolean unreadOnly;

    public RetrieveNotificationRequest(@JsonProperty("userid") UUID userId_U,@JsonProperty("unreadonly") boolean unreadOnly) {
        this.userId_U = userId_U;
        this.userId_S = this.userId_U.toString();
        this.unreadOnly = unreadOnly;
    }

    public RetrieveNotificationRequest(@JsonProperty("userid") String userId_S,@JsonProperty("unreadonly") boolean unreadOnly) {
        this.userId_S = userId_S;
        this.userId_U = UUID.fromString(this.userId_S);
        this.unreadOnly = unreadOnly;
    }

    public String getUserId_S() {
        return userId_S;
    }

    public UUID getUserId_U() {
        return userId_U;
    }

    public boolean isUnreadOnly() {
        return unreadOnly;
    }
}
