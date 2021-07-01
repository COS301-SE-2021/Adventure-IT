package com.adventureit.adventureservice;

import com.adventureit.adventureservice.Entity.Adventure;
import com.adventureit.adventureservice.Repository.AdventureRepository;
import com.adventureit.adventureservice.Responses.GetAdventuresByUserUUIDResponse;
import com.adventureit.adventureservice.Service.AdventureServiceImplementation;
import com.adventureit.adventureservice.Exceptions.AdventureNotFoundException;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

public class AdventureServiceUnitTests {
    private final AdventureRepository adventureRepository = Mockito.mock(AdventureRepository.class);
    private final AdventureServiceImplementation adventureService = new AdventureServiceImplementation(adventureRepository);

    final UUID validUserID1 = UUID.randomUUID();
    final UUID validUserID2 = UUID.randomUUID();
    Adventure mockAdventure1 = new Adventure("Mock Adventure 1", UUID.randomUUID(), validUserID1);
    Adventure mockAdventure2 = new Adventure("Mock Adventure 2", UUID.randomUUID(), validUserID1);
    Adventure mockAdventure3 = new Adventure("Mock Adventure 3", UUID.randomUUID(), validUserID1);

    @Test
    @Description("Ensuring that the creator of a number of adventures can view these adventures")
    public void creatorExistingAdventures_ReturnAdventures(){
        Mockito.when(adventureRepository.findByOwnerId(validUserID1)).thenReturn(List.of(mockAdventure1, mockAdventure2, mockAdventure3));

        GetAdventuresByUserUUIDResponse res = adventureService.getAdventureByOwnerUUID(validUserID1);
        Assertions.assertEquals(res.getAdventures().size(), 3);
    }

    @Test
    @Description("Ensuring that a user who has not created any adventures cannot view any adventures")
    public void creatorNoAdventures_ReturnNoAdventureFound(){
            Assertions.assertThrows(AdventureNotFoundException.class, ()->{
                GetAdventuresByUserUUIDResponse res = adventureService.getAdventureByOwnerUUID(validUserID2);
            });
    }

    @Test
    @Description("Ensuring that an attendee of multiple adventures can view these adventures")
    public void attendeeExistingAdventures_ReturnAdventures(){
        Mockito.when(adventureRepository.findByAttendees(validUserID2)).thenReturn(List.of(mockAdventure1, mockAdventure2));

        GetAdventuresByUserUUIDResponse res = adventureService.getAdventureByAttendeeUUID(validUserID2);
        Assertions.assertEquals(res.getAdventures().size(), 2);
    }

    @Test
    @Description("Ensuring that an attendee of multiple adventures can view these adventures")
    public void attendeeExistingAdventures_ReturnNoAdventureFound(){
        Assertions.assertThrows(AdventureNotFoundException.class, ()->{
            GetAdventuresByUserUUIDResponse res = adventureService.getAdventureByAttendeeUUID(validUserID2);
        });
    }



}
