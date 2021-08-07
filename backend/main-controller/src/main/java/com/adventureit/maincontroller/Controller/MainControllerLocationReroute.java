package com.adventureit.maincontroller.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/location")
public class MainControllerLocationReroute {

    private RestTemplate restTemplate = new RestTemplate();

    private final String IP = "localhost";
    private final String locationPort = "9006";

    @GetMapping("/test")
    public String locationTest(){
        return restTemplate.getForObject("http://"+ IP + ":" + locationPort + "/location/test", String.class);
    }

    @GetMapping(value="/create/{location}")
    public String createLocation(@PathVariable String location) {
        return restTemplate.getForObject("http://"+ IP + ":" + locationPort + "/location/create/" + location, String.class);
    }
}
