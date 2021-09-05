package com.adventureit.recommendationservice.service;

import com.adventureit.recommendationservice.entity.Location;
import com.adventureit.recommendationservice.entity.User;
import com.adventureit.recommendationservice.exception.*;
import com.adventureit.recommendationservice.repository.LocationRepository;
import com.adventureit.recommendationservice.repository.UserRepository;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.simple.SimpleMatrix;
import org.ejml.simple.ops.SimpleOperations_DSCC;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void getUserRecommendations(UUID id){
        // Get users
        List<User> users = this.userRepository.findAll();
        int numUsers = users.size();

        // Get locations
        List<Location> locations = this.locationRepository.findAll();
        int numLocations = locations.size();

        if(numUsers == 0){
            throw new RecommendationException("No persisted users, cannot create recommendations");
        }

        if(numLocations == 0){
            throw new RecommendationException("No persisted locations, cannot create recommendations");
        }

        // For each user (row) add an entry with their "rating" of the corresponding location (col)
        DMatrixSparseCSC datasetMatrix = new DMatrixSparseCSC(numUsers, numLocations);

        for(int i = 0; i < numUsers; i++){
            for(int j = 0; j < numLocations; j++){
                User currentUser = users.get(i);
                Location currentLocation = locations.get(j);

                // If user has liked the location and visited the location, insert rating 4
                if(currentUser.hasLiked(currentLocation) && currentUser.hasVisited(currentLocation)){
                    datasetMatrix.set(i, j, 4);
                }
                // If user has liked the location, insert rating 2
                else if(currentUser.hasLiked(currentLocation)){
                    datasetMatrix.set(i, j, 2);
                }
                // If user has visited the location, insert rating 1
                else if (currentUser.hasVisited(currentLocation)){
                    datasetMatrix.set(i, j, 1);
                }
                // If user has neither liked nor visited the location, don't insert anything
            }
        }
        SimpleOperations_DSCC matrixOps = new SimpleOperations_DSCC();
        double a = datasetMatrix.dot(new SimpleMatrix(datasetMatrix.transpose()));
        SimpleMatrix norms = datasetMatrix;
        norms.zero();
        norms = norms.diag(a);

        norms.print();
    }
}

