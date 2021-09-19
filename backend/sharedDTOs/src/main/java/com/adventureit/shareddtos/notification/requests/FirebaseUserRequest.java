package com.adventureit.shareddtos.notification.requests;

import java.util.UUID;

public class FirebaseUserRequest {
    private UUID userId;
    private String firebaseToken;

    public FirebaseUserRequest(UUID userId, String firebaseToken) {
        this.userId = userId;
        this.firebaseToken = firebaseToken;
    }

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
