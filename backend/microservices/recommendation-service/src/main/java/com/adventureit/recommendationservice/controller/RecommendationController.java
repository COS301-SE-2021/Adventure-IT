package com.adventureit.recommendationservice.controller;
import com.adventureit.recommendationservice.service.RecommendationService;
import com.adventureit.recommendationservice.exception.LocationExistsException;
import com.adventureit.recommendationservice.exception.LocationNotFoundException;
import com.adventureit.recommendationservice.exception.UserExistsException;
import com.adventureit.recommendationservice.exception.UserNotFoundException;
import com.adventureit.shareddtos.recommendation.request.CreateLocationRequest;
import com.adventureit.shareddtos.recommendation.request.CreateUserRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {


    private RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/test")
    public String test(){
        return "Recommendation Controller is functional";
    }

    // User likes a location
    @GetMapping("like/{userId}/{locationId}")
    public String likeLocation(@PathVariable UUID userId, @PathVariable UUID locationId){
        try {
            this.recommendationService.likeLocation(userId, locationId);
        }
        catch (UserNotFoundException e){
            return "User not found";
        }
        catch(LocationNotFoundException e){
            return "Location not found";
        }
        catch(Exception e){
            return "Bad request";
        }
        return "Location successfully liked";
    }

    // User visits a location
    @GetMapping("visit/{userId}/{locationId}")
    public String visitLocation(@PathVariable UUID userId, @PathVariable UUID locationId){
        try {
            this.recommendationService.visitLocation(userId, locationId);
        }
        catch (UserNotFoundException e){
            return "User not found";
        }
        catch(LocationNotFoundException e){
            return "Location not found";
        }
        catch(Exception e){
            return "Bad request";
        }
        return "Location successfully visited";

    }

    // Add a new user
    @PostMapping("add/user")
    public String addUser(@RequestBody CreateUserRequest req){
        try {
            this.recommendationService.addUser(req.userId);
        }
        catch(UserExistsException e){
            return "Bad request";
        }

        return "User added successfully";
    }

    // Add a new location
    @PostMapping("add/location")
    public String addLocation(@RequestBody CreateLocationRequest req){

        try {
            this.recommendationService.addLocation(req.locationId, req.getLocationString());
        }
        catch(LocationExistsException e){
            return "Not successful";
        }
        return "Successful!";
    }


    // User requests arbitrary number of recommendations
    @GetMapping("get/{userId}/{numRecommendations}/{location}")
    public String[][] getUserRecommendations(@PathVariable UUID userId, @PathVariable String numRecommendations, @PathVariable String location){
        return this.recommendationService.getUserRecommendations(userId, numRecommendations, location);
    }

    // User requests arbitrary number of popular locations
    @GetMapping("get/popular/{userId}/{numPopular}/{location}")
    public String[][] getMostPopular(@PathVariable UUID userId, @PathVariable String numPopular, @PathVariable String location){
        return this.recommendationService.getMostPopular(userId,numPopular,location);
    }
}
