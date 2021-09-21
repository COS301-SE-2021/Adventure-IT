package com.adventureit.maincontroller.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/test")
    public String mainControllerTest(){
        return "Main controller functional";
    }
}
