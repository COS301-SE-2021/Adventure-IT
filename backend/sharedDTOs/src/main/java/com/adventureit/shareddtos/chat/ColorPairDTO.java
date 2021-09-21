package com.adventureit.shareddtos.chat;

import java.util.UUID;

public class ColorPairDTO {
    UUID colorPairId;
    UUID userID;
    UUID adventureId;
    int color;

    public ColorPairDTO() {
    }

    public ColorPairDTO(UUID colorPairId, UUID userID, UUID adventureId, int color) {
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
