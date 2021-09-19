package com.adventureit.maincontroller.controller;


import com.adventureit.shareddtos.notification.NotificationDTO;
import com.adventureit.shareddtos.notification.requests.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class MainControllerNotificationReroute {


    private final RestTemplate restTemplate = new RestTemplate();

    String IP = "http://localhost";
    String notificationPort = "9004";

    @PostMapping("/sendemail")
    public String sendEmail(@RequestBody SendEmailRequest req){
        return restTemplate.postForObject(IP + ":" + notificationPort + "/notification/sendemail/", req, String.class);

    }

    @PostMapping("/createNotification")
    public String createNotification(@RequestBody CreateNotificationRequest req){
        return restTemplate.postForObject(IP + ":" + notificationPort + "/notification/createNotification/", req, String.class);

    }

    @PostMapping("/retrieveNotification")
    public List<NotificationDTO> test3(@RequestBody RetrieveNotificationRequest req){
        return restTemplate.postForObject(IP + ":" + notificationPort + "/notification/retrieveNotification/", req, List.class);
    }

    @PostMapping("/sendFirebaseNotification")
    public String sendFirebaseNotification(@RequestBody SendFirebaseNotificationRequest req){
        return restTemplate.postForObject(IP + ":" + notificationPort + "/notification/sendFirebaseNotification/", req, String.class);
    }

    @PostMapping("/addFirebaseUser")
    public String addFirebaseUser(@RequestBody FirebaseUserRequest req){
        return restTemplate.postForObject(IP + ":" + notificationPort + "/notification/addFirebaseUser/", req, String.class);
    }

}


