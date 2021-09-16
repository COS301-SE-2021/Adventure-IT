package com.adventureit.recommendationservice.controller;

import com.adventureit.recommendationservice.exception.LocationExistsException;
import com.adventureit.recommendationservice.exception.LocationNotFoundException;
import com.adventureit.recommendationservice.exception.UserExistsException;
import com.adventureit.recommendationservice.exception.UserNotFoundException;
import com.adventureit.shareddtos.recommendation.request.CreateLocationRequest;
import com.adventureit.shareddtos.recommendation.request.CreateUserRequest;
import com.adventureit.recommendationservice.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    @Autowired
    RecommendationService recommendationService;

    // User likes a location
    @GetMapping("like/{userId}/{locationId}")
    public ResponseEntity<String> likeLocation(@PathVariable UUID userId, @PathVariable UUID locationId){
        try {
            this.recommendationService.likeLocation(userId, locationId);
        }
        catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch(LocationNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Location Liked Successfully");
    }

    // User visits a location
    @GetMapping("visit/{userId}/{locationId}")
    public ResponseEntity<String> visitLocation(@PathVariable UUID userId, @PathVariable UUID locationId){
        try {
            this.recommendationService.visitLocation(userId, locationId);
        }
        catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch(LocationNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Location visited Successfully");

    }

    // Add a new user
    @PostMapping("add/user")
    public ResponseEntity<String> addUser(@RequestBody CreateUserRequest req){
        try {
            this.recommendationService.addUser(req.userId);
        }
        catch(UserExistsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body("User added successfully");
    }

    // Add a new location
    @PostMapping("add/location")
    public ResponseEntity<String> addLocation(@RequestBody CreateLocationRequest req){
        try {
            this.recommendationService.addLocation(req.locationId);
        }
        catch(LocationExistsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Location added successfully");
    }


    // User requests arbitrary number of recommendations
    @GetMapping("get/{userId}/{numRecommendations}/{location}")
    public String[][] getUserRecommendations(@PathVariable UUID userId, @PathVariable String numRecommendations, @PathVariable String location){
        return this.recommendationService.getUserRecommendations(userId, numRecommendations, location);
    }

    // User requests arbitrary number of popular locations
    @GetMapping("get/popular/{numPopular}/{location}")
    public String[][] getMostPopular(@PathVariable UUID userId, @PathVariable String numPopular, @PathVariable String location){
        return this.recommendationService.getMostPopular(userId,numPopular,location);
    }
}
