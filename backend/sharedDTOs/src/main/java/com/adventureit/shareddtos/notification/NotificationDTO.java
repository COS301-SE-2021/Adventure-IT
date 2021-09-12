package com.adventureit.shareddtos.notification;

import java.util.Date;
import java.util.UUID;


public class NotificationDTO {

    private UUID notificationID;

    private UUID userID;

    private String payload;

    private Date createdDateTime;

    private Date readDateTime;

    public NotificationDTO(UUID notificationID,
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

    public NotificationDTO() {

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