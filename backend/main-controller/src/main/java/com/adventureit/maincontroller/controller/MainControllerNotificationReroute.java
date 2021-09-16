package com.adventureit.maincontroller.controller;


import com.adventureit.maincontroller.service.MainControllerServiceImplementation;
import com.adventureit.shareddtos.notification.NotificationDTO;
import com.adventureit.shareddtos.notification.requests.CreateNotificationRequest;
import com.adventureit.shareddtos.notification.requests.RetrieveNotificationRequest;
import com.adventureit.shareddtos.notification.requests.SendEmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class MainControllerNotificationReroute {

    private final RestTemplate restTemplate = new RestTemplate();
    private MainControllerServiceImplementation service;

    private final String IP = "http://localhost";
    private final String notificationPort = "9004";

    @Autowired
    public MainControllerNotificationReroute(MainControllerServiceImplementation service) {
        this.service = service;
    }

    @PostMapping("/sendemail")
    public String sendEmail(@RequestBody SendEmailRequest req) throws Exception {
        String[] ports = {notificationPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.postForObject(IP + ":" + notificationPort + "/notification/sendemail/", req, String.class);

    }

    @PostMapping("/createNotification")
    public String createNotification(@RequestBody CreateNotificationRequest req) throws Exception {
        String[] ports = {notificationPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.postForObject(IP + ":" + notificationPort + "/notification/createNotification/", req, String.class);
    }

    @PostMapping("/retrieveNotification")
    public List<NotificationDTO> test3(@RequestBody RetrieveNotificationRequest req) throws Exception {
        String[] ports = {notificationPort};
        service.pingCheck(ports,restTemplate);
        return restTemplate.postForObject(IP + ":" + notificationPort + "/notification/retrieveNotification/", req, List.class);
    }

}


