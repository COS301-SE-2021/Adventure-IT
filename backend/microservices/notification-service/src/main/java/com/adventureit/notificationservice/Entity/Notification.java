package com.adventureit.notificationservice.Entity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Table(name="notifications")
@Entity(name="Notifications")
public class Notification{

    @Id
    private UUID NotificationID;

    @Column
    private UUID UserID;

    @Column
    private String payload;

    @Column
    private Date createdDateTime;

    @Column
    private Date readDateTime;





    public Notification(){

    }


    public  Notification(UUID notificationID,
                                 UUID userID,
                                 String payload,
                                 Date createdDateTime,
                                 Date readDateTime){

        this.NotificationID = notificationID;
        this.UserID = userID;
        this.payload = payload;
        this.createdDateTime = createdDateTime;
        this.readDateTime = readDateTime;

    }

    public UUID getUserID(){
        return this.UserID;
    }

    public UUID getNotificationID(){
        return this.NotificationID;
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