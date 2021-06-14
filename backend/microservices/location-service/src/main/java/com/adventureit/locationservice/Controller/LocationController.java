package com.adventureit.locationservice.Controller;



import com.adventureit.locationservice.Service.LocationServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/** This class implements the functionality of the UserAPI interface.*/
@CrossOrigin("*")
@RestController
public class LocationController {

//    private final LocationServiceImplementation service;

//    @Autowired
//    public LocationController(LocationServiceImplementation service){
//        this.service = service;
//    }

//    @PostMapping(value = "api/LocationTest", consumes = "application/json", produces = "application/json")
//    public String test(){
//        return "working";
//    }

    @GetMapping(value="/location/test")
    public String test(){
        return "location controller is working";
    }


}
