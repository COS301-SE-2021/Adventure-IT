package com.adventureit.notificationservice.Controllers;

import com.adventureit.notificationservice.Service.notificationService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    void sendEmail(@JsonProperty("email")String email,@JsonProperty("subject") String subject, @JsonProperty("body") String message ){
        notification.sendEmail(email,subject,message);
    }
}