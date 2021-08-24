package com.adventureit.notificationservice.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;


@Entity(name="Notifications")
public class Notification{

    @Id
    private UUID notificationID;

    @Column
    private UUID userID;

    @Column
    private String payload;

    @Column
    private Date createdDateTime;

    @Column
    private Date readDateTime;

    public  Notification(UUID notificationID,
                                 UUID userID,
                                 String payload,
                                 Date createdDateTime,
                                 Date readDateTime){

        this.notificationID = notificationID;
        this.userID = userID;
        this.payload = payload;
        this.createdDateTime = createdDateTime;
        this.readDateTime = readDateTime;

    }

    public Notification() {

    }


    public UUID getUserID(){
        return this.userID;
    }

    public UUID getNotificationID(){
        return this.notificationID;
    }

    public String getPayload(){
        return this.payload;
    }

    public Date getCreatedDateTime(){
        return this.createdDateTime;
    }

    public Date getReadDateTime(){
        return this.readDateTime;
    }

    public void setReadDateTime(Date readDateTime){
        this.readDateTime = readDateTime;
    }
}