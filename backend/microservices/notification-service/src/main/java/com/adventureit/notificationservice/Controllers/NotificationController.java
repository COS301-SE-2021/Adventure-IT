package com.adventureit.notificationservice.Controllers;

import com.adventureit.notificationservice.Requests.sendEmailRequest;
import com.adventureit.notificationservice.Service.notificationService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    String sendEmail(@RequestBody sendEmailRequest req){
        notification.sendEmail(req.getEmail() ,req.getSubject(), req.getBody());
        return"success";
    }

    @PostMapping("/test2")
    String test2(){
        notification.createNotification();
        return "create works";
    }
}