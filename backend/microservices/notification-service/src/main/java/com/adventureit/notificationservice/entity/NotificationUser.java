package com.adventureit.notificationservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class NotificationUser {
    @Id
    UUID userId;
    String firebaseToken;

    public NotificationUser(UUID userId, String firebaseToken) {
        this.userId = userId;
        this.firebaseToken = firebaseToken;
    }

    public NotificationUser() {}

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }
}
