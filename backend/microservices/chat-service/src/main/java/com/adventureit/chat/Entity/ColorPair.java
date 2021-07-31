package com.adventureit.chat.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class ColorPair {
    @Id
    UUID userID;
    int color;

    public ColorPair(UUID userID, int color) {
        this.userID = userID;
        this.color = color;
    }

    public ColorPair() {

    }

    public UUID getUserID() {
        return userID;
    }

    public int getColor() {
        return color;
    }
}
