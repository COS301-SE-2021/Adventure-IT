package com.adventureit.userservice.Entities;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Collection;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name="users", schema = "public")
public class Users implements UserDetails {



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
     * @param userID
     * @param firstname
     * @param lastname
     * @param email
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

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    // Only here to satisfy inheritance constraint (will NOT be used for anything)
    @Override
    public String getPassword() {
        return null;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }
}
