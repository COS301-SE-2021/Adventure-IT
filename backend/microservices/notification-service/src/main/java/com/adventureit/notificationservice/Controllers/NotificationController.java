package com.adventureit.notificationservice.Controllers;

import com.adventureit.notificationservice.Entity.Notification;
import com.adventureit.notificationservice.Requests.CreateNotificationRequest;
import com.adventureit.notificationservice.Requests.RetrieveNotificationRequest;
import com.adventureit.notificationservice.Requests.SendEmailRequest;
import com.adventureit.notificationservice.Service.notificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    private notificationService notification;

    @Autowired
    public NotificationController(notificationService notification) {
        this.notification = notification;
    }

    @GetMapping("/test")
    String test(){
        return "Notification Controller is functional";
    }



    @PostMapping("/sendemail")
    String sendEmail(@RequestBody SendEmailRequest req){
        notification.sendEmail(req.getEmail() ,req.getSubject(), req.getBody());
        return"success";
    }

    @PostMapping("/createNotification")
    String createNotification(@RequestBody CreateNotificationRequest req){
        notification.createNotification(req);
        return "create works";
    }

    @GetMapping("/retrieveNotification")
    List<Notification> test3(@RequestBody RetrieveNotificationRequest req){
        return notification.retrieveNotifications(req);
    }



}