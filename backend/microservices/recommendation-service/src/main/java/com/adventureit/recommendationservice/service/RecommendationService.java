package com.adventureit.recommendationservice.service;

import com.adventureit.recommendationservice.entity.RecommendedLocation;
import com.adventureit.recommendationservice.entity.User;
import com.adventureit.recommendationservice.exception.*;
import com.adventureit.recommendationservice.repository.LocationRepository;
import com.adventureit.recommendationservice.repository.UserRepository;
import org.ejml.simple.SimpleMatrix;
import org.springframework.stereotype.Service;

import java.util.*;

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
        RecommendedLocation foundLocation = this.locationRepository.findLocationByLocationId(locationId);

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
        RecommendedLocation foundLocation = this.locationRepository.findLocationByLocationId(locationId);

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
            this.locationRepository.save(new RecommendedLocation(locationId));
        }
        else {
            throw new LocationExistsException(locationId);
        }
    }

    public String[][] getUserRecommendations(UUID id, int numRecommendations, String location){
        // Get users
        List<User> users = this.userRepository.findAll();
        int numUsers = users.size();

        // Find index of current user:
        int userIndex = -1;
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getUserId().equals(id)){
                userIndex = i;
                break;
            }
        }

        if(userIndex == -1){
            throw new UserNotFoundException(id);
        }

        // Get locations
        List<RecommendedLocation> locations = this.locationRepository.findAll();
        int numLocations = locations.size();

        if(numUsers == 0){
            String[][] returnMatrix = new String[numLocations][2];
            return returnMatrix;
        }

        if(numLocations == 0){
            String[][] returnMatrix = new String[numLocations][2];
            return returnMatrix;
        }

        // For each user (row) add an entry with their "rating" of the corresponding location (col)
        SimpleMatrix datasetMatrix = new SimpleMatrix(numUsers, numLocations);

        for(int i = 0; i < numUsers; i++){
            for(int j = 0; j < numLocations; j++){
                User currentUser = users.get(i);
                RecommendedLocation currentLocation = locations.get(j);

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
        SimpleMatrix transposedDatasetMatrix = datasetMatrix.transpose();
        SimpleMatrix similarityMatrix = new SimpleMatrix(datasetMatrix);
        similarityMatrix = transposedDatasetMatrix.mult(datasetMatrix);
        SimpleMatrix norms = similarityMatrix.diag();

        for (int i = 0; i < norms.numRows(); i++) {
            for (int j = 0; j < norms.numCols(); j++) {
                norms.set(i,j, Math.sqrt(norms.get(i,j)));
            }
        }

        for (int i = 0; i < similarityMatrix.numRows(); i++) {
            for (int j = 0; j < similarityMatrix.numCols(); j++) {
                double curValue = similarityMatrix.get(i,j);
                double newValue = curValue / norms.get(j,0) / norms.transpose().get(0,j);
                if(Double.isNaN(newValue)){
                    similarityMatrix.set(i,j, 0);
                }
                else {
                    similarityMatrix.set(i,j, newValue);
                }
            }
        }

        SimpleMatrix x = new SimpleMatrix(1, similarityMatrix.numCols());

        for (int i = 0; i < similarityMatrix.numRows(); i++) {
            x = x.plus(similarityMatrix.extractVector(true, i));
        }

        SimpleMatrix predictionMatrix = datasetMatrix.mult(similarityMatrix);

        for (int i = 0; i < predictionMatrix.numRows() - 1; i++) {
            for (int j = 0; j < x.numCols() - 1; j++) {
                double prediction = predictionMatrix.get(i,j)/x.get(0,j);
                if(Double.isNaN(prediction) || prediction == Double.POSITIVE_INFINITY){
                    prediction = 0;
                }
                predictionMatrix.set(i, j, prediction);
            }
        }

        double[] locationPredictedRating = new double[predictionMatrix.numCols()];
        int[] locationIndex = new int[predictionMatrix.numCols()];


        for (int i = 0; i < predictionMatrix.numCols(); i++) {
            locationPredictedRating[i] = predictionMatrix.get(userIndex, i);
            locationIndex[i] = i;
        }

        for (int i = 0; i < locationPredictedRating.length - 1; i++) {
            for (int j = i; j < locationPredictedRating.length; j++) {
                if(locationPredictedRating[i] < locationPredictedRating[j]){
                    double tempPrediction = locationPredictedRating[i];
                    locationPredictedRating[i] = locationPredictedRating[j];
                    locationPredictedRating[j] = tempPrediction;

                    int tempIndex = locationIndex[i];
                    locationIndex[i] = locationIndex[j];
                    locationIndex[j] = tempIndex;
                }
            }
        }
        User user = users.get(userIndex);
        List<UUID> recommendedLocations = new ArrayList<>();
        for (int i = 0; i < locationIndex.length; i++) {
            recommendedLocations.add(locations.get(i).getLocationId());
        }

        if(numRecommendations>= recommendedLocations.size()){
            numRecommendations = recommendedLocations.size();
        }
        String[][] returnMatrix = new String[numLocations][2];
        for(int i = 0; i<numRecommendations;i++){
            RecommendedLocation currentLocation = locationRepository.findLocationByLocationId(recommendedLocations.get(i));
            returnMatrix[i][0] = recommendedLocations.get(i).toString();
            returnMatrix[i][1] = user.hasLiked(currentLocation).toString();
        }

        return returnMatrix;
    }

    public String[][] getMostPopular(UUID id, int numPopular, String location) {
        List<RecommendedLocation> locations = locationRepository.findAll();
        locations.sort(Comparator.comparing(RecommendedLocation::getVisits));
        String[][] returnMatrix = new String[locations.size()][2];
        List<User> users = this.userRepository.findAll();

        if(locations.size()==0)
        {
            return returnMatrix;
        }

        // Find index of current user:
        int userIndex = -1;
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getUserId().equals(id)){
                userIndex = i;
                break;
            }
        }

        if(userIndex == -1){
            throw new UserNotFoundException(id);
        }
        User user=users.get(userIndex);

        for(int i = 0; i<numPopular;i++){
            returnMatrix[i][0] = locations.get(i).getLocationId().toString();
            returnMatrix[i][1] = user.hasLiked(locations.get(i)).toString();
        }

        return returnMatrix;
    }
}

