package com.adventureit.maincontroller.Controller;

import com.adventureit.userservice.Responses.GetUserByUUIDDTO;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RestController
public class MainControllerUserReroute {

    @Autowired
    private EurekaClient eurekaClient;

    private RestTemplate restTemplate = new RestTemplate();


    @RequestMapping("/api/GetUser/{id}")
    public GetUserByUUIDDTO getUserByUUID(@PathVariable UUID id){
        InstanceInfo userInstance =eurekaClient.getApplication("USER-SERVICE").getInstances().get(0);
        String userIP = userInstance.getIPAddr();
        int userPort = userInstance.getPort();
        return restTemplate.getForObject("http://"+ userIP + ":" + userPort + "/user/GetUser/"+id.toString(), GetUserByUUIDDTO.class);
    }
}


