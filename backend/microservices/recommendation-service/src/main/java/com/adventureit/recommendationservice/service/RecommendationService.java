package com.adventureit.recommendationservice.service;

import com.adventureit.recommendationservice.entity.Location;
import com.adventureit.recommendationservice.entity.User;
import com.adventureit.recommendationservice.exception.LocationExistsException;
import com.adventureit.recommendationservice.exception.LocationNotFoundException;
import com.adventureit.recommendationservice.exception.UserExistsException;
import com.adventureit.recommendationservice.exception.UserNotFoundException;
import com.adventureit.recommendationservice.repository.LocationRepository;
import com.adventureit.recommendationservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.UUID;

@Service
public class RecommendationService {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    public RecommendationService(UserRepository userRepository, LocationRepository locationRepository){
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

    public void likeLocation(UUID userId, UUID locationId){
        User foundUser = this.userRepository.findUserByUserId(userId);
        Location foundLocation = this.locationRepository.findLocationByLocationId(locationId);

        if(foundUser == null){
            throw new UserNotFoundException(userId);
        }

        if(foundLocation == null){
            throw new LocationNotFoundException(locationId);
        }
        foundUser.likeLocation(foundLocation);
        foundLocation.like();
    }

    public void visitLocation(UUID userId, UUID locationId){
        User foundUser = this.userRepository.findUserByUserId(userId);
        Location foundLocation = this.locationRepository.findLocationByLocationId(locationId);

        if(foundUser == null){
            throw new UserNotFoundException(userId);
        }

        if(foundLocation == null){
            throw new LocationNotFoundException(locationId);
        }
        foundUser.visitLocation(foundLocation);;
        foundLocation.visit();
    }

    public void addUser(UUID userId){
        if(this.userRepository.findUserByUserId(userId) == null){
            this.userRepository.save(new User(userId));
        }
        else {
            throw new UserExistsException(userId);
        }
    }


    public void addLocation(UUID locationId) {
        if(this.locationRepository.findLocationByLocationId(locationId) == null){
            this.locationRepository.save(new Location(locationId));
        }
        else {
            throw new LocationExistsException(locationId);
        }
    }
}
