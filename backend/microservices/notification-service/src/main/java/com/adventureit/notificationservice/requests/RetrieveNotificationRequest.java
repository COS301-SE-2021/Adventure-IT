package com.adventureit.notificationservice.requests;



import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class RetrieveNotificationRequest {
    private final String userIdS;
    private final UUID userIdU;
    private final boolean unreadOnly;

    public RetrieveNotificationRequest(@JsonProperty("userid") UUID userIdU,@JsonProperty("unreadOnly") boolean unreadOnly) {
        this.userIdU = userIdU;
        this.userIdS = this.userIdU.toString();
        this.unreadOnly = unreadOnly;
    }

    public RetrieveNotificationRequest(@JsonProperty("userid") String userIdS, @JsonProperty("unreadOnly") boolean unreadOnly) {
        this.userIdS = userIdS;
        this.userIdU = UUID.fromString(this.userIdS);
        this.unreadOnly = unreadOnly;
    }

    public String getUserIdS() {
        return userIdS;
    }

    public UUID getUserIdU() {
        return userIdU;
    }

    public boolean isUnreadOnly() {
        return unreadOnly;
    }
}
