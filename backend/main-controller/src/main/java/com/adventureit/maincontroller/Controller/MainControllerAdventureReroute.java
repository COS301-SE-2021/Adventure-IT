package com.adventureit.maincontroller.Controller;

import com.adventureit.maincontroller.Requests.Adventure.CreateAdventureRequest;
import com.adventureit.maincontroller.Service.ConnectionFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/adventure")
public class MainControllerAdventureReroute {

    @Autowired
    private EurekaClient eurekaClient;



    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping("/test")
    public String adventureTest(){
        InstanceInfo adventureInstance =eurekaClient.getApplication("ADVENTURE-SERVICE").getInstances().get(0);
        String adventureIP = adventureInstance.getIPAddr();
        int adventurePort = adventureInstance.getPort();
        return restTemplate.getForObject("http://"+ adventureIP + ":" + adventurePort + "/adventure/test", String.class);
    }

    @PostMapping("/create")
    public String createAdventure(@RequestBody CreateAdventureRequest req) {
        String url = "http://127.0.0.1:9001/adventure/create";
        System.out.println(req.getEndDate());
        String[] fields = {
                "name:" + req.getName() + ";",
                "description:" + req.getDescription() + ";",
                "ownerId:" + req.getOwnerId() + ";",
                "startDate:" + req.getStartDate() + ";",
                "endDate:" + req.getEndDate() + ";",
        };
        ConnectionFactory connection = new ConnectionFactory(fields, url, 0.1);
        return connection.buildConnection();
    }

}


