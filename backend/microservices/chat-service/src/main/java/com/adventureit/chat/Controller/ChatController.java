package com.adventureit.chat.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @GetMapping("/test")
    String test(){
        return "Budget Controller is functioning";
    }




}
