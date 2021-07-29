package com.adventureit.chat.Entity;

import java.util.UUID;

public class ColorPair {
    final UUID userID;
    final int color;

    public ColorPair(UUID userID, int color) {
        this.userID = userID;
        this.color = color;
    }

    public UUID getUserID() {
        return userID;
    }

    public int getColor() {
        return color;
    }
}
