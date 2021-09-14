package com.adventureit.recommendationservice;

import com.adventureit.recommendationservice.entity.Location;
import com.adventureit.recommendationservice.entity.User;
import com.adventureit.recommendationservice.repository.LocationRepository;
import com.adventureit.recommendationservice.repository.UserRepository;
import com.adventureit.recommendationservice.service.RecommendationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class RecommendationServiceUnitTests {

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    LocationRepository locationRepository = Mockito.mock(LocationRepository.class);;

    RecommendationService recommendationService = new RecommendationService(userRepository, locationRepository);

    Random rand = new Random();

    static List<User> mockUsers;
    static List<Location> mockLocations;

    void createMockEntries(){
        final int numMockUsers = 5;
        final int numMockLocations = 20;

        // Create mock users
        mockUsers = new ArrayList<User>();
        for (int i = 0; i < numMockUsers; i++) {
            mockUsers.add(new User());
        }

        // Create mock locations
        mockLocations = new ArrayList<Location>();
        for (int i = 0; i < numMockLocations; i++) {
            mockLocations.add(new Location());
        }

        // Create mock "interactions"
        for (User user : mockUsers) {

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
        Mockito.when(userRepository.findAll()).thenReturn(mockUsers);
        Mockito.when(locationRepository.findAll()).thenReturn(mockLocations);
    }

    @Test
    void testGetRecommendations(){
        createMockEntries();
        this.recommendationService.getUserRecommendations(UUID.randomUUID(),15);
    }
}

