package com.adventureit.locationservice.controller;

import com.adventureit.shareddtos.location.responses.CurrentLocationResponseDTO;
import com.adventureit.shareddtos.location.responses.LocationResponseDTO;
import com.adventureit.locationservice.service.LocationServiceImplementation;
import com.adventureit.shareddtos.location.responses.LocationsResponseDTO;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
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

    @GetMapping(value = "/getLocations/{ids}")
    public LocationsResponseDTO getLocations(@PathVariable List<UUID> ids){
        return locationServiceImplementation.getLocations(ids);
    }

    @GetMapping(value = "/getCurrentLocation/{userID}")
    public CurrentLocationResponseDTO getCurrentLocation(@PathVariable UUID userID){
        return locationServiceImplementation.getCurrentLocation(userID);
    }

    @GetMapping(value = "/storeCurrentLocation/{userID}/{latitude}/{longitude}")
    public void storeCurrentLocation(@PathVariable UUID userID, @PathVariable String latitude, @PathVariable String longitude){
        locationServiceImplementation.storeCurrentLocation(userID,latitude,longitude);
    }

    @GetMapping(value = "/compareGeography/{id}/{userID}")
    public boolean compareGeography(@PathVariable UUID id, @PathVariable UUID userID) throws JSONException, IOException {
        return locationServiceImplementation.compareGeometry(id,userID);
    }

    @GetMapping(value = "/addLike/{id}")
    public void addLike(@PathVariable UUID id){
        locationServiceImplementation.addLike(id);
    }

    @GetMapping(value = "/addVisit/{id}")
    public void addVisit(@PathVariable UUID id){
        locationServiceImplementation.addVisit(id);
    }

    @GetMapping(value = "/addFlagLocation/{locationID}/{userID}")
    public void addFlagLocation(@PathVariable UUID locationID, @PathVariable UUID userID){
        locationServiceImplementation.addFlagLocation(locationID,userID);
    }

    @GetMapping(value = "/getFlagList/{userID}")
    public List<String> getFlagList(@PathVariable UUID locationID, @PathVariable UUID userID){
        return locationServiceImplementation.getFlagList(userID);
    }
}
