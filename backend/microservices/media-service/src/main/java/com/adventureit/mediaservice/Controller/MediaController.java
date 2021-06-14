package com.adventureit.mediaservice.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/media")
public class MediaController {
    @GetMapping("/test")
    String test(){
        return "Media Controller is functional";
    }
}
