package com.adventureit.shareddtos.user.responses;

public class LoginUserDTO {
    private final boolean success;
    private final String message;

    public LoginUserDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
