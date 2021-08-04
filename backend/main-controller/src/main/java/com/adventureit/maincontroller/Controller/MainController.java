package com.adventureit.maincontroller.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/main")
public class MainController {

//    @Autowired
//    private EurekaClient eurekaClient;

    private RestTemplate restTemplate = new RestTemplate();
    private final String IP = "localhost";
    private final String adventurePort = "9001";
    private final String locationPort = "9006";

//    @RequestMapping("/adventure/test")
//    public String adventureTest(){
//        InstanceInfo adventureInstance =eurekaClient.getApplication("ADVENTURE-SERVICE").getInstances().get(0);
//        String adventureIP = adventureInstance.getIPAddr();
//        int adventurePort = adventureInstance.getPort();
//        return restTemplate.getForObject("http://"+ adventureIP + ":" + adventurePort + "/adventure/test", String.class);
//    }

    @GetMapping("/test")
    public String mainControllerTest(){
        return "Main controller is working";
    }

/*
    @RequestMapping("/api/GetUser/{id}")
    public GetUserByUUIDResponse getUserByUUID(@PathVariable UUID id){
        InstanceInfo adventureInstance =eurekaClient.getApplication("ADVENTURE-SERVICE").getInstances().get(0);
        String adventureIP = adventureInstance.getIPAddr();
        int adventurePort = adventureInstance.getPort();
        return restTemplate.getForObject("http://"+ adventureIP + ":" + adventurePort + "/adventure/test", String.class);
    }*/

    @GetMapping("/adventure/test")
    public String adventureTest(){
        return restTemplate.getForObject("http://"+ IP + ":" + adventurePort + "/adventure/test", String.class);
    }

    @GetMapping("adventure/getAttendees/{id}")
    public List getAttendees(@PathVariable UUID id) throws Exception {
        return restTemplate.getForObject("http://"+ IP + ":" + adventurePort + "/adventure/getAttendees/" + id, List.class);
    }

    

}


