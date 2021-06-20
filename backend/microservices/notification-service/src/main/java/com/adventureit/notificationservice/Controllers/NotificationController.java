package com.adventureit.notificationservice.Controllers;

import com.adventureit.notificationservice.Entity.Notification;
import com.adventureit.notificationservice.Requests.CreateNotificationRequest;
import com.adventureit.notificationservice.Requests.RetrieveNotificationRequest;
import com.adventureit.notificationservice.Requests.SendEmailRequest;
import com.adventureit.notificationservice.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    private NotificationService notification;

    @Autowired
    public NotificationController(NotificationService notification) {
        this.notification = notification;
    }

    @GetMapping("/test")
    String test(){
        return "Notification Controller is functional";
    }



    @GetMapping("/sendemail/{email}")
    String sendEmail(@PathVariable(value = "email") String email){
        notification.sendEmail(email);
        return"Send Email Successful";
    }

    @PostMapping("/createNotification")
    String createNotification(@RequestBody CreateNotificationRequest req){
        notification.createNotification(req);
        return "Create Notifictaion Successful";
    }

    @GetMapping("/retrieveNotification")
    List<Notification> test3(@RequestBody RetrieveNotificationRequest req){
        return notification.retrieveNotifications(req);
    }



}