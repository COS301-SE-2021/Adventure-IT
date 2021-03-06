package com.adventureit.userservice.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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
    private String emergencyContact;
    private Boolean enabled = false;
    @Lob
    private byte [] profilePicture;
    private Boolean locked = false;
    private String fireBaseId;
    // true is dark(default) false is light
    private Boolean theme;
    private long storageUsed = 0;

    public Boolean getNotificationSettings() {
        return notificationSettings;
    }

    public void setNotificationSettings() {
        if(this.notificationSettings.equals(false))
        {
            this.notificationSettings=true;
        }
        else
        {
            this.notificationSettings=false;
        }
    }

    private Boolean notificationSettings;
    @ElementCollection
    private List<UUID> likedLocations = new ArrayList<>();
    @ElementCollection
    private List<UUID> visitedLocations = new ArrayList<>();




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
        this.enabled = false;
        this.theme = false;
        this.notificationSettings=false;
        this.fireBaseId = "";
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


    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getTheme() {
        return theme;
    }

    public void setTheme(Boolean theme) {
        this.theme = theme;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<UUID> getLikedLocations() {
        return likedLocations;
    }

    public void setLikedLocations(List<UUID> likedLocations) {
        this.likedLocations = likedLocations;
    }

    public void setVisitedLocations(List<UUID> visitedLocations) {
        this.visitedLocations = visitedLocations;
    }

    public List<UUID> getVisitedLocations() {
        return visitedLocations;
    }

    public long getStorageUsed() {
        return storageUsed;
    }

    public void setStorageUsed(long storageUsed) {
        this.storageUsed = storageUsed;
    }
}
