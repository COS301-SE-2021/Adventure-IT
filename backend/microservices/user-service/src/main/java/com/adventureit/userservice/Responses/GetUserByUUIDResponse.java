package com.adventureit.userservice.Responses;


import com.adventureit.userservice.Entities.Users;

public class GetUserByUUIDResponse {

    private Users user;

    /**
     * This ojcect will store the response attributes from the GetUserByUUID service, currently a mock
     * user will be sent back for testing purposes but for future implementation a user will be found from the database
     *
     *
     * @param user user to be returned
     */
    public GetUserByUUIDResponse( Users user) {

        this.user = user;
    }





    public Users getUser() {
        return this.user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
