package com.adventureit.locationservice.Controller;



import com.adventureit.locationservice.Service.LocationServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/** This class implements the functionality of the UserAPI interface.*/
@CrossOrigin("*")
@RestController
public class LocationController {

    @GetMapping(value="/location/test")
    public String test(){
        return "location controller is working";
    }


}
