package com.adventureit.chat.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class ColorPair {
    @Id
    UUID userID;
    UUID adventureId;
    int color;

    public ColorPair(UUID userID, UUID adventureId, int color) {
        this.userID = userID;
        this.adventureId = adventureId;
        this.color = color;
    }

    public ColorPair() {

    }

    public UUID getAdventureId() {
        return adventureId;
    }

    public UUID getUserID() {
        return userID;
    }

    public int getColor() {
        return color;
    }
}
