package com.adventureit.locationservice.controller;

import com.adventureit.locationservice.responses.LocationResponseDTO;
import com.adventureit.locationservice.service.LocationServiceImplementation;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

/** This class implements the functionality of the UserAPI interface.*/
@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    LocationServiceImplementation locationServiceImplementation;

    @GetMapping(value="/test")
    public String test(){
        return "location controller is working";
    }

    @GetMapping(value="/create/{location}")
    public UUID create(@PathVariable String location) throws JSONException, IOException {
        return locationServiceImplementation.createLocation(location);
    }

    @GetMapping(value = "/getLocation/{id}")
    public LocationResponseDTO getLocation(@PathVariable UUID id){
        return locationServiceImplementation.getLocation(id);
    }
}
