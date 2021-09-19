package com.adventureit.shareddtos.user.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class SetUserThemeRequest {
    private final UUID userId;
    private final Boolean theme;

    public SetUserThemeRequest(@JsonProperty("userId")UUID userId, @JsonProperty("theme") Boolean theme) {
        this.userId = userId;
        this.theme = theme;
    }

    public UUID getUserId() {
        return userId;
    }

    public Boolean getTheme() {
        return theme;
    }
}
