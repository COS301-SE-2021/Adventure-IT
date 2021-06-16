package com.adventureit.adventureservice.Controllers;

import com.adventureit.adventureservice.Entity.Adventure;
import com.adventureit.adventureservice.Service.AdventureServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/adventure")
public class AdventureController {
    @Autowired
    AdventureServiceImplementation adventureServiceImplementation;

    @GetMapping("/test")
    public String test(){
        return "Adventure Controller is functional \n";
    }

    @GetMapping("/populate")
    public String populate(){
        adventureServiceImplementation.mockPopulate();
        return "Mock adventures populated \n";
    }

    @GetMapping("/all")
    public List<Adventure> retrieveAllAdventures() throws Exception{
        try {
            return adventureServiceImplementation.getAllAdventures().getAdventures();
        }
        catch (Exception e){
            throw e;
        }

    }
}
