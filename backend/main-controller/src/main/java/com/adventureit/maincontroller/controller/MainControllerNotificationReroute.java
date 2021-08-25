package com.adventureit.maincontroller.controller;


import com.adventureit.notificationservice.entity.Notification;
import com.adventureit.notificationservice.requests.CreateNotificationRequest;
import com.adventureit.notificationservice.requests.RetrieveNotificationRequest;
import com.adventureit.notificationservice.requests.SendEmailRequest;
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
    public List<Notification> test3(@RequestBody RetrieveNotificationRequest req){
        return restTemplate.postForObject(IP + ":" + notificationPort + "/notification/retrieveNotification/", req, List.class);
    }

}


