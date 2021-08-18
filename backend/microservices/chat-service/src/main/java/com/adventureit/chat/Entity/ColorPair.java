package com.adventureit.chat.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class ColorPair {
    @Id
    UUID colorPairId;
    UUID userID;
    UUID adventureId;
    int color;

    public ColorPair() {
    }

    public ColorPair(UUID colorPairId, UUID userID, UUID adventureId, int color) {
        this.colorPairId = colorPairId;
        this.userID = userID;
        this.adventureId = adventureId;
        this.color = color;
    }

    public UUID getColorPairId() {
        return colorPairId;
    }

    public UUID getUserID() {
        return userID;
    }

    public UUID getAdventureId() {
        return adventureId;
    }

    public int getColor() {
        return color;
    }
}
