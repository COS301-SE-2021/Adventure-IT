package com.adventureit.userservice.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name="users", schema = "public")
public class Users  {



    @Id
    private UUID userID;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private Boolean enabled = false;
    @Lob
    private byte [] profilePicture;
    private Boolean locked = false;




    /**
     * User model Constructor which takes in the following parameters:
     * @param userID user Id for this particular user.
     * @param firstname first name of the user
     * @param lastname last name of the user
     * @param email email address of the user
     */

    public Users(UUID userID, String username, String firstname, String lastname, String email) {
        this.userID = userID;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.enabled =false;
    }

    /**
     * Default constructor for User Model
     */
    public Users() {

    }

    public String getUsername() {
        return username;
    }


    /**
     * User service to retrieve users User ID
     * @return userID
     */
    public UUID getUserID() {
        return userID;
    }



    /**
     * User service to retrieve users User ID
     * @return firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * User service to retrieve users last name
     * @return lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * User service to retrieve users email
     * @return email
     */
    public String getEmail() {
        return email;
    }

}
