package com.adventureit.maincontroller.responses;

import com.adventureit.userservice.responses.GetUserByUUIDDTO;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class RegisteredUsersDTO {
    private GetUserByUUIDDTO user;
    private boolean checkedIn;

    public RegisteredUsersDTO() {
    }

    public RegisteredUsersDTO(GetUserByUUIDDTO user, boolean check){
        this.checkedIn=check;
        this.user=user;
    }

    public GetUserByUUIDDTO getUser() {
        return user;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }
}
