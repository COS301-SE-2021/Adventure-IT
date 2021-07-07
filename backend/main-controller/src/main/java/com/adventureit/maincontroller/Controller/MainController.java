package com.adventureit.maincontroller.Controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MainController {

    @Autowired
    private EurekaClient eurekaClient;

    private final RestTemplate restTemplate = new RestTemplate();

    @RequestMapping("/adventure/test")
    public String adventureTest(){
        InstanceInfo adventureInstance =eurekaClient.getApplication("ADVENTURE-SERVICE").getInstances().get(0);
        String adventureIP = adventureInstance.getIPAddr();
        int adventurePort = adventureInstance.getPort();
        return restTemplate.getForObject("http://"+ adventureIP + ":" + adventurePort + "/adventure/test", String.class);
    }

    @RequestMapping("/user/test")
    public String userTest(){
        InstanceInfo userInstance =eurekaClient.getApplication("USER-SERVICE").getInstances().get(0);
        String userIP = userInstance.getIPAddr();
        int userPort = userInstance.getPort();
        return restTemplate.getForObject("http://"+ userIP + ":" + userPort + "/user/test", String.class);
    }
}


