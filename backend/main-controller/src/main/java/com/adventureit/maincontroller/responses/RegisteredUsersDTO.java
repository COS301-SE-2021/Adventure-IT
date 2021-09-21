package com.adventureit.maincontroller.responses;

import com.adventureit.shareddtos.user.responses.GetUserByUUIDDTO;

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
