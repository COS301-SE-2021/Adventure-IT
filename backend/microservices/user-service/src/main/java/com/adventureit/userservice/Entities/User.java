package com.adventureit.userservice.Entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name="User")
public class User {

    @Id
    private UUID userID;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phoneNumber;




    /**
     * User model Constructor which takes in the following parameters:
     * @param userID
     * @param firstname
     * @param lastname
     * @param email
     * @param password
     * @param phoneNumber
     */

    public User(UUID userID,String username, String firstname, String lastname, String email, String password, String phoneNumber) {
        this.userID = userID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.username = username;
    }

    /**
     * Default constructor for User Model
     */
    public User() {

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
     * User service to set User ID
     * @param userID
     */
    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    /**
     * User service to retrieve users User ID
     * @return firstname
     */
    public String getFirstname() {
        return firstname;
    }
    /**
     * User service to set User ID
     * @param firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    /**
     * User service to retrieve users last name
     * @return lastname
     */
    public String getLastname() {
        return lastname;
    }
    /**
     * User service to set User ID
     * @param lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    /**
     * User service to retrieve users email
     * @return email
     */
    public String getEmail() {
        return email;
    }
    /**
     * User service to set User ID
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * User service to retrieve users User password
     * @return password
     */
    public String getPassword() {
        return password;
    }
    /**
     * User service to set User ID
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * User service to retrieve users User phone number
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
     * User service to set User ID
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
