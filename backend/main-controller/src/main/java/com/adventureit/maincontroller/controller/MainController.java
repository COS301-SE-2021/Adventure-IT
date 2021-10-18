package com.adventureit.maincontroller.controller;

import io.micrometer.core.annotation.Timed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/test")
    @Timed(value = "maincontroller.test.time", description = "Time taken to return main controller test")
    public String mainControllerTest(){
        return "Main controller functional";
    }
}
