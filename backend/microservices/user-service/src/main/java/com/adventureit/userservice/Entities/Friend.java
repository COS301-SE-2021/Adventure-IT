package com.adventureit.userservice.Entities;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;

import java.util.UUID;

@Entity
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID firstUser;
    private UUID secondUser;
    private Date createdDate;
    boolean accepted;

    public Friend(UUID firstUser, UUID secondUser){
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        accepted = false;
    }

    public Friend() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public UUID getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(UUID firstUser) {
        this.firstUser = firstUser;
    }

    public UUID getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(UUID secondUser) {
        this.secondUser = secondUser;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isAccepted() {
        return accepted;
    }
}
