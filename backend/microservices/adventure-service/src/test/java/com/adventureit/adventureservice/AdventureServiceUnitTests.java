package com.adventureit.adventureservice;

import com.adventureit.adventureservice.Entity.Adventure;
import com.adventureit.adventureservice.Repository.AdventureRepository;
import com.adventureit.adventureservice.Requests.CreateAdventureRequest;
import com.adventureit.adventureservice.Requests.GetAdventureByUUIDRequest;
import com.adventureit.adventureservice.Responses.*;
import com.adventureit.adventureservice.Service.AdventureServiceImplementation;
import com.adventureit.adventureservice.Exceptions.AdventureNotFoundException;
import jdk.jfr.Description;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdventureServiceUnitTests {
    private final AdventureRepository adventureRepository = Mockito.mock(AdventureRepository.class);
    private final AdventureServiceImplementation adventureService = new AdventureServiceImplementation(adventureRepository);

    final UUID validUserID1 = UUID.randomUUID();
    final UUID validUserID2 = UUID.randomUUID();
    final UUID adventureId1 = UUID.randomUUID();
    final UUID adventureId2 = UUID.randomUUID();
    final UUID adventureId3 = UUID.randomUUID();
    final UUID invalidAdventureId = UUID.randomUUID();

    Adventure mockAdventure1 = new Adventure("Mock Adventure 1","Mock Description 1", adventureId1, validUserID1, LocalDate.of(2021, 1, 1),LocalDate.of(2021, 1, 1),UUID.randomUUID());
    Adventure mockAdventure2 = new Adventure("Mock Adventure 2","Mock Description 1", adventureId2, validUserID1, LocalDate.of(2021, 1, 1),LocalDate.of(2021, 1, 1),UUID.randomUUID());
    Adventure mockAdventure3 = new Adventure("Mock Adventure 3","Mock Description 1", adventureId3, validUserID1, LocalDate.of(2021, 1, 1),LocalDate.of(2021, 1, 1),UUID.randomUUID());


    /**
     * Testing Request Objects
     */

    @Test
    @Description("Testing CreateAdventureRequest to make sure that all parameters passed in are correctly retrieved")
    public void createAdventureRequestTest(){
        //Given
        String name = "Test name";
        String description = "Test description";
        UUID ownerId = UUID.randomUUID();
        String startDate = "Wednesday, 21/08/2009 ";
        String endDate = "Friday, 15/07/2010 ";
        String location = "Johannesburg";

        //When
        CreateAdventureRequest request = new CreateAdventureRequest(name,description,ownerId,startDate,endDate,location);

        //Then
        Assertions.assertEquals(name,request.getName());
        Assertions.assertEquals(description,request.getDescription());
        Assertions.assertEquals(ownerId,request.getOwnerId());
        Assertions.assertEquals(startDate,request.getStartDate());
        Assertions.assertEquals(endDate,request.getEndDate());
        Assertions.assertEquals(location,request.getLocation());
    }

    @Test
    @Description("Testing GetAdventureRequest to make sure that all parameters passed in are correctly retrieved")
    public void getAdventureByUUIDRequestTest(){
        //Given
        UUID adventureId = UUID.randomUUID();
        //When
        GetAdventureByUUIDRequest request = new GetAdventureByUUIDRequest(adventureId);

        //Then
        Assertions.assertEquals(adventureId,request.getId());
    }


    /**
     * Testing Response Objects
     */

    @Test
    @Description("Testing GetAdventureRequest to make sure that all parameters passed in are correctly retrieved")
    public void createAdventureResponseTest(){
        //Given
        Boolean success = true;
        String message = "Adventure was successfully created";
        Adventure mockAdventure = new Adventure("Mock Adventure 1","Mock Description 1", adventureId1, validUserID1, LocalDate.of(2021, 1, 1),LocalDate.of(2021, 1, 1),UUID.randomUUID());

        //When
        CreateAdventureResponse response = new CreateAdventureResponse(success,message,mockAdventure);

        //Then
        Assertions.assertEquals(success, response.isSuccess());
        Assertions.assertEquals(message, response.getMessage());
        Assertions.assertEquals(mockAdventure, response.getAdventure());
    }

    @Test
    @Description("Testing GetAdventureByUUIDResponse to make sure that all parameters passed in are correctly retrieved")
    public void getAdventureByUUIDResponseTest(){
        //Given
        Boolean success = true;
        String message = "Adventure was successfully created";
        Adventure mockAdventure = new Adventure("Mock Adventure 1","Mock Description 1", adventureId1, validUserID1, LocalDate.of(2021, 1, 1),LocalDate.of(2021, 1, 1),UUID.randomUUID());

        //When
        CreateAdventureResponse response = new CreateAdventureResponse(success,message,mockAdventure);

        //Then
        Assertions.assertEquals(success, response.isSuccess());
        Assertions.assertEquals(message, response.getMessage());
        Assertions.assertEquals(mockAdventure, response.getAdventure());
    }




    @Test
    @Description("Ensuring that the creator of a number of adventures can view these adventures")
    public void creatorExistingAdventures_ReturnAdventures(){
        Mockito.when(adventureRepository.findByOwnerId(validUserID1)).thenReturn(List.of(mockAdventure1, mockAdventure2, mockAdventure3));



    }

    @Test
    @Description("Ensuring that a user who has not created any adventures cannot view any adventures")
    public void creatorNoAdventures_ReturnNoAdventureFound(){
            Assertions.assertThrows(AdventureNotFoundException.class, ()->{
                List<GetAdventuresByUserUUIDResponse> res = adventureService.getAdventureByOwnerUUID(validUserID2);
            });
    }

//    @Test
//    @Description("Ensuring that an attendee of multiple adventures can view these adventures")
//    public void attendeeExistingAdventures_ReturnAdventures(){
//        Mockito.when(adventureRepository.findByAttendees(validUserID2)).thenReturn(List.of(mockAdventure1, mockAdventure2));
//
//        List<GetAdventuresByUserUUIDResponse> res = adventureService.getAdventureByAttendeeUUID(validUserID2);
//        Assertions.assertEquals(2,res.size());
//    }

    @Test
    @Description("Ensuring that an attendee of multiple adventures can view these adventures")
    public void attendeeExistingAdventures_ReturnNoAdventureFound(){
        Assertions.assertThrows(AdventureNotFoundException.class, ()-> adventureService.getAdventureByAttendeeUUID(validUserID2));
    }

//    @Test
//    @Description("Ensuring that all adventures are returned when there are existing adventures")
//    public void getAllAdventuresExistingAdventures_ReturnAdventures(){
//        Mockito.when(adventureRepository.findAll()).thenReturn(List.of(mockAdventure1, mockAdventure2, mockAdventure3));
//        List<GetAllAdventuresResponse> res = adventureService.getAllAdventures();
//        Assertions.assertEquals(3,res.size() );
//    }

    @Test
    @Description("Ensuring that the appropriate exception is thrown when getAllAdventures is called without any adventures stored")
    public void getAllAdventuresNoAdventures_ThrowNoAdventureFound(){
        Assertions.assertThrows(AdventureNotFoundException.class, ()-> adventureService.getAllAdventures());
    }

    @Test
    @Description("Ensuring that the removal of an existing adventure leads to the adventure being removed")
    public void removeAdventureExistingAdventure_SuccessfulRemoval(){
        Mockito.when(adventureRepository.findAdventureByAdventureId(adventureId1)).thenReturn(mockAdventure1);
        Assertions.assertDoesNotThrow(()->{
            RemoveAdventureResponse res = adventureService.removeAdventure(adventureId1,validUserID1);
            Assertions.assertEquals(res.getMessage(), "Adventure successfully removed");
        });
    }

    @Test
    @Description("Ensuring that attempting to remove a non-existent adventure throws the appropriate exception")
    public void removeAdventureNoAdventure_FailedRemoval(){

        Assertions.assertThrows(AdventureNotFoundException.class, ()->{
           adventureService.removeAdventure(invalidAdventureId,validUserID1);
        });
    }

    @Test
    @Description("Ensuring that when an existing adventure is retrieved using its UUID, the corresponding adventure is returned")
    public void getAdventureByUUIDExistingAdventure_ReturnAdventure(){
        final UUID mockId = UUID.randomUUID();
        Mockito.when(adventureRepository.findByAdventureId(mockId)).thenReturn(mockAdventure1);
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
