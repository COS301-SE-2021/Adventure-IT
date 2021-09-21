package com.adventureit.notificationservice.controllers;

import com.adventureit.notificationservice.entity.Notification;
import com.adventureit.shareddtos.notification.requests.*;
import com.adventureit.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notification;

    @Autowired
    public NotificationController(NotificationService notification) {
        this.notification = notification;
    }

    @GetMapping("/test")
    public String test(){
        return "Notification Controller is functional";
    }



    @PostMapping("/sendemail")
    public String sendEmail(@RequestBody SendEmailRequest req){
        notification.sendEmail(req.getEmail() ,req.getSubject(), req.getBody());
        return"Send Email Successful";
    }

    @PostMapping("/createNotification")
    public String createNotification(@RequestBody CreateNotificationRequest req){
        notification.createNotification(req);
        return "Create Notifictaion Successful";
    }

    @GetMapping("/retrieveNotification")
    public List<Notification> test3(@RequestBody RetrieveNotificationRequest req){
        return notification.retrieveNotifications(req);
    }

    @PostMapping("/sendFirebaseNotification")
    @Consumes("application/json")
    public String sendFirebaseNotification(@RequestBody SendFirebaseNotificationRequest req){
    return notification.sendFirebaseNotification(req);
    }

    @PostMapping("/sendFirebaseNotifications")
    @Consumes("application/json")
    public String sendFirebaseNotifications(@RequestBody SendFirebaseNotificationsRequest req){
        return notification.sendFirebaseNotifications(req);
    }

    @PostMapping("/addFirebaseUser")
    @Consumes("application/json")
    public String addFirebaseUser(@RequestBody FirebaseUserRequest req){
        return notification.addFirebaseUser(req);
    }

}