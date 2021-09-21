package com.adventureit.shareddtos.chat.requests;

import java.util.List;
import java.util.UUID;

public class GetUsersRequestDTO {
    List<UUID> users;

    public GetUsersRequestDTO(List<UUID> users) {
        this.users = users;
    }

    public GetUsersRequestDTO() {
    }

    public List<UUID> getUsers() {
        return users;
    }

    public void setUsers(List<UUID> users) {
        this.users = users;
    }
}
