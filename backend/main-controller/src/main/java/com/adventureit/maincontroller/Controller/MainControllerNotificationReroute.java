package com.adventureit.maincontroller.Controller;


import com.adventureit.notificationservice.Entity.Notification;
import com.adventureit.notificationservice.Requests.CreateNotificationRequest;
import com.adventureit.notificationservice.Requests.RetrieveNotificationRequest;
import com.adventureit.notificationservice.Requests.SendEmailRequest;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notification")
public class MainControllerNotificationReroute {

    @Autowired
    private EurekaClient eurekaClient;

    private RestTemplate restTemplate = new RestTemplate();

    String IP = "localhost";
    String notificationPort = "9004";

    @PostMapping("/sendemail")
    String sendEmail(@RequestBody SendEmailRequest req){
        return restTemplate.postForObject("http://"+ IP + ":" + notificationPort + "/notification/sendemail/", req, String.class);

    }

    @PostMapping("/createNotification")
    String createNotification(@RequestBody CreateNotificationRequest req){
        return restTemplate.postForObject("http://"+ IP + ":" + notificationPort + "/notification/createNotification/", req, String.class);

    }

    @PostMapping("/retrieveNotification")
    List<Notification> test3(@RequestBody RetrieveNotificationRequest req){
        return restTemplate.postForObject("http://"+ IP + ":" + notificationPort + "/notification/retrieveNotification/", req, List.class);
    }

}


