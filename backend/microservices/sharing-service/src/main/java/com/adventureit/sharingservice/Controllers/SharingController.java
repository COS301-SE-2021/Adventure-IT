package com.adventureit.sharingservice.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sharing")
public class SharingController {
    @GetMapping("/test")
    String test(){
        return "Sharing Controller is functional";
    }
}