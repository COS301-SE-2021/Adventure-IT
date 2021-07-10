package com.adventureit.adventureservice;

import com.adventureit.adventureservice.Entity.Adventure;
import com.adventureit.adventureservice.Repository.AdventureRepository;
import com.adventureit.adventureservice.Requests.GetAdventureByUUIDRequest;
import com.adventureit.adventureservice.Responses.GetAdventureByUUIDResponse;
import com.adventureit.adventureservice.Responses.GetAdventuresByUserUUIDResponse;
import com.adventureit.adventureservice.Responses.GetAllAdventuresResponse;
import com.adventureit.adventureservice.Responses.RemoveAdventureResponse;
import com.adventureit.adventureservice.Service.AdventureServiceImplementation;
import com.adventureit.adventureservice.Exceptions.AdventureNotFoundException;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AdventureServiceUnitTests {
    private final AdventureRepository adventureRepository = Mockito.mock(AdventureRepository.class);
    private final AdventureServiceImplementation adventureService = new AdventureServiceImplementation(adventureRepository);

    final UUID validUserID1 = UUID.randomUUID();
    final UUID validUserID2 = UUID.randomUUID();
    Adventure mockAdventure1 = new Adventure("Mock Adventure 1","Mock Description 1", UUID.randomUUID(), validUserID1, LocalDate.of(2021, 1, 1),LocalDate.of(2021, 1, 1));
    Adventure mockAdventure2 = new Adventure("Mock Adventure 2","Mock Description 1", UUID.randomUUID(), validUserID1, LocalDate.of(2021, 1, 1),LocalDate.of(2021, 1, 1));
    Adventure mockAdventure3 = new Adventure("Mock Adventure 3","Mock Description 1", UUID.randomUUID(), validUserID1, LocalDate.of(2021, 1, 1),LocalDate.of(2021, 1, 1));

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

    @Test
    @Description("Ensuring that all adventures are returned when there are existing adventures")
    public void getAllAdventuresExistingAdventures_ReturnAdventures(){
        Mockito.when(adventureRepository.findAll()).thenReturn(List.of(mockAdventure1, mockAdventure2, mockAdventure3));
        GetAllAdventuresResponse res = adventureService.getAllAdventures();
        Assertions.assertEquals(res.getAdventures().size(), 3);
    }

    @Test
    @Description("Ensuring that the appropriate exception is thrown when getAllAdventures is called without any adventures stored")
    public void getAllAdventuresNoAdventures_ThrowNoAdventureFound(){
        Assertions.assertThrows(AdventureNotFoundException.class, ()->{
            GetAllAdventuresResponse res = adventureService.getAllAdventures();
        });
    }

    @Test
    @Description("Ensuring that the removal of an existing adventure leads to the adventure being removed")
    public void removeAdventureExistingAdventure_SuccessfulRemoval(){
        final UUID mockId = UUID.randomUUID();
        Mockito.when(adventureRepository.findAdventureByAdventureId(mockId)).thenReturn(mockAdventure1);
        Assertions.assertDoesNotThrow(()->{
            RemoveAdventureResponse res = adventureService.removeAdventure(mockId);
            Assertions.assertEquals(res.getMessage(), "Adventure successfully removed");
        });
    }

    @Test
    @Description("Ensuring that attempting to remove a non-existent adventure throws the appropriate exception")
    public void removeAdventureNoAdventure_FailedRemoval(){
        final UUID mockId = UUID.randomUUID();
        Assertions.assertThrows(AdventureNotFoundException.class, ()->{
            RemoveAdventureResponse res = adventureService.removeAdventure(mockId);
        });
    }

    @Test
    @Description("Ensuring that when an existing adventure is retrieved using its UUID, the corresponding adventure is returned")
    public void getAdventureByUUIDExistingAdventure_ReturnAdventure(){
        final UUID mockId = UUID.randomUUID();
        Mockito.when(adventureRepository.findById(mockId)).thenReturn(mockAdventure1);
        GetAdventureByUUIDRequest req = new GetAdventureByUUIDRequest(mockId);
        GetAdventureByUUIDResponse res = adventureService.getAdventureByUUID(req);
        Assertions.assertEquals(res.getAdventure(), mockAdventure1);
    }

    @Test
    @Description("Ensuring that when a non-existent adventure is retrieved, the retrieval fails")
    public void getAdventureByUUIDNoAdventure_FailedRetrieval(){
        final UUID mockId = UUID.randomUUID();
        GetAdventureByUUIDRequest req = new GetAdventureByUUIDRequest(mockId);
        Assertions.assertThrows(AdventureNotFoundException.class, ()->{
            GetAdventureByUUIDResponse res = adventureService.getAdventureByUUID(req);
        });
    }






}
