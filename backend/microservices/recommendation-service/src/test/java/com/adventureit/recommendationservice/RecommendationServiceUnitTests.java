package com.adventureit.recommendationservice;

import com.adventureit.recommendationservice.entity.RecommendedLocation;
import com.adventureit.recommendationservice.entity.RecommendedUser;
import com.adventureit.recommendationservice.repository.RecommendedLocationRepository;
import com.adventureit.recommendationservice.repository.RecommendedUserRepository;
import com.adventureit.recommendationservice.service.RecommendationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class RecommendationServiceUnitTests {
    RecommendedUserRepository recommendedUserRepository = Mockito.mock(RecommendedUserRepository.class);
    RecommendedLocationRepository recommendedLocationRepository = Mockito.mock(RecommendedLocationRepository.class);;

    RecommendationService recommendationService = new RecommendationService(recommendedUserRepository, recommendedLocationRepository);

    Random rand = new Random();

    static List<RecommendedUser> mockUsers;
    static List<RecommendedLocation> mockLocations;

    void createMockEntries(){
        final int numMockUsers = 5;
        final int numMockLocations = 20;

        // Create mock users
        mockUsers = new ArrayList<RecommendedUser>();
        for (int i = 0; i < numMockUsers; i++) {
            mockUsers.add(new RecommendedUser());
        }

        // Create mock locations
        mockLocations = new ArrayList<RecommendedLocation>();
        for (int i = 0; i < numMockLocations; i++) {
            mockLocations.add(new RecommendedLocation());
        }

        // Create mock "interactions"
        for (RecommendedUser user : mockUsers) {

            // Choose between 1 and 1/4 of the number of mock locations to "interact" with
            int numberOfInteractedLocations = rand.nextInt(numMockLocations / 4) + 1;
            List<Integer> chosenLocations = new ArrayList<>();

            // Choose a random location to interact with (without interacting with the same location twice)
            for (int i = 0; i < numberOfInteractedLocations; i++) {
                int chosenLocation;
                do {
                    chosenLocation = rand.nextInt(mockLocations.size());
                }
                while(chosenLocations.contains(chosenLocation));
                chosenLocations.add(chosenLocation);
            }

            // Perform random interactions with chosen locations
            for (int i = 0; i < numberOfInteractedLocations; i++) {
                int interaction = rand.nextInt(1000);
                int decision = interaction % 3;
                switch(decision){
                    case 0:
                        // Visit location but don't like
                        user.visitLocation(mockLocations.get(chosenLocations.get(i)));
                        mockLocations.get(chosenLocations.get(i)).visit();
                        break;
                    case 1:
                        // Like location but don't visit
                        user.likeLocation(mockLocations.get(chosenLocations.get(i)));
                        mockLocations.get(chosenLocations.get(i)).like();
                        break;
                    case 2:
                        // Like and visit location
                        user.likeLocation(mockLocations.get(chosenLocations.get(i)));
                        user.visitLocation(mockLocations.get(chosenLocations.get(i)));
                        mockLocations.get(chosenLocations.get(i)).like();
                        mockLocations.get(chosenLocations.get(i)).visit();
                        break;
                }
            }
        }

        // Ensure mocked repositories return mocked entries
        Mockito.when(recommendedUserRepository.findAll()).thenReturn(mockUsers);
        Mockito.when(recommendedLocationRepository.findAll()).thenReturn(mockLocations);
    }

//    @Test
//    void testGetRecommendations(){
//        createMockEntries();
//        this.recommendationService.getUserRecommendations(UUID.randomUUID(),"15","Paris, France");
//    }
}

