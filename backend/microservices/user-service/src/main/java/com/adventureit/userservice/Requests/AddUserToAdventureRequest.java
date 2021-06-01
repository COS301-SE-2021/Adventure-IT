package com.adventureit.userservice.Requests;

public class AddUserToAdventureRequest {
    private String fname;
    private String lname;
    private String phoneNum;
    private String adventureName;

    public AddUserToAdventureRequest(String fname, String lname, String phoneNum, String adventureName)
    {
        this.fname = fname;
        this.lname = lname;
        this.phoneNum = phoneNum;
        this.adventureName = adventureName;
    }
}
