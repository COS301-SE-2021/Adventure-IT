package com.adventureit.maincontroller.controller;


import com.adventureit.maincontroller.exceptions.ControllerNotAvailable;
import com.adventureit.maincontroller.service.MainControllerServiceImplementation;
import com.adventureit.shareddtos.notification.NotificationDTO;
import com.adventureit.shareddtos.notification.requests.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class MainControllerNotificationReroute {

    private final RestTemplate restTemplate = new RestTemplate();
    private final MainControllerServiceImplementation service;

    private static final String INTERNET_PORT = "http://localhost";
    private static final String NOTIFICATION_PORT = "9004";

    @Autowired
    public MainControllerNotificationReroute(MainControllerServiceImplementation service) {
        this.service = service;
    }

    @PostMapping("/sendemail")
    public String sendEmail(@RequestBody SendEmailRequest req) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {NOTIFICATION_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.postForObject(INTERNET_PORT + ":" + NOTIFICATION_PORT + "/notification/sendemail/", req, String.class);

    }

    @PostMapping("/createNotification")
    public String createNotification(@RequestBody CreateNotificationRequest req) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {NOTIFICATION_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.postForObject(INTERNET_PORT + ":" + NOTIFICATION_PORT + "/notification/createNotification/", req, String.class);
    }

    @PostMapping("/retrieveNotification")
    public List<NotificationDTO> test3(@RequestBody RetrieveNotificationRequest req) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {NOTIFICATION_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.postForObject(INTERNET_PORT + ":" + NOTIFICATION_PORT + "/notification/retrieveNotification/", req, List.class);
    }

    @PostMapping("/sendFirebaseNotification")
    public String sendFirebaseNotification(@RequestBody SendFirebaseNotificationRequest req) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {NOTIFICATION_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.postForObject(INTERNET_PORT + ":" + NOTIFICATION_PORT + "/notification/sendFirebaseNotification/", req, String.class);
    }

    @PostMapping("/addFirebaseUser")
    public String addFirebaseUser(@RequestBody FirebaseUserRequest req) throws ControllerNotAvailable, InterruptedException {
        String[] ports = {NOTIFICATION_PORT};
        service.pingCheck(ports,restTemplate);
        return restTemplate.postForObject(INTERNET_PORT + ":" + NOTIFICATION_PORT + "/notification/addFirebaseUser/", req, String.class);
    }

}


