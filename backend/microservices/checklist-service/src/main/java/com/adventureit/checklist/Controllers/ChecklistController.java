package com.adventureit.checklist.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checklist")
public class ChecklistController {
    @GetMapping("/test")
    String test(){
        return "Checklist Controller is functional";
    }
}
