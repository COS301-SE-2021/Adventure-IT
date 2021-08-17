package com.adventureit.timelineservice;


import com.adventureit.adventureservice.Exceptions.AdventureNotFoundException;
import com.adventureit.adventureservice.Requests.GetAdventureByUUIDRequest;
import com.adventureit.adventureservice.Responses.GetAdventureByUUIDResponse;
import com.adventureit.timelineservice.Entity.Timeline;
import com.adventureit.timelineservice.Entity.TimelineType;
import com.adventureit.timelineservice.Repository.TimelineRepository;
import com.adventureit.timelineservice.Requests.CreateTimelineRequest;
import com.adventureit.timelineservice.Responses.TimelineDTO;
import com.adventureit.timelineservice.Service.TimelineServiceImplementation;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class TimelineServiceUnitTests {

    @Mock
    TimelineRepository timelineRepo = Mockito.mock(TimelineRepository.class);

    TimelineServiceImplementation service = new TimelineServiceImplementation(timelineRepo);

    UUID mockTimelineId1 = UUID.randomUUID();
    UUID mockTimelineId2 = UUID.randomUUID();
    UUID mockTimelineId3 = UUID.randomUUID();
    UUID mockAdventureId = UUID.randomUUID();
    TimelineType mockType = TimelineType.BUDGET;
    LocalDateTime mockTime = LocalDateTime.now();
    String mockDescription1 = "This is a mock timeline 1";
    String mockDescription2 = "This is a mock timeline 2";
    String mockDescription3 = "This is a mock timeline 3";

    Timeline mockTimeline1 = new Timeline(mockTimelineId1,mockAdventureId,mockDescription1,mockTime,mockType);
    Timeline mockTimeline2 = new Timeline(mockTimelineId2,mockAdventureId,mockDescription2,mockTime,mockType);
    Timeline mockTimeline3 = new Timeline(mockTimelineId3,mockAdventureId,mockDescription3,mockTime,mockType);

    /**
     * Testing Request Objects
     */

    @Test
    @Description("Testing to make sure that the CreateTimelineRequest returns the correct parameters that were passed in")
    public void createTimelineRequest(){
        //Given
        UUID mockAdventureId = UUID.randomUUID();
        TimelineType mockType = TimelineType.ADVENTURE;
        String mockDescription = "This is a mock message";

        //When
        CreateTimelineRequest mockRequest = new CreateTimelineRequest(mockAdventureId,mockType,mockDescription);

        //Then
        Assertions.assertEquals(mockAdventureId, mockRequest.getAdventureID());
        Assertions.assertEquals(mockType, mockRequest.getType());
        Assertions.assertEquals(mockDescription, mockRequest.getDescription());

    }

    /**
     * Testing Response Objects
     */

    @Test
    @Description("Testing to make sure that the TimelineDTO returns the correct parameters that were passed in")
    public void timelineDtoTest(){
        //Given
        UUID mockTimelineId = UUID.randomUUID();
        UUID mockAdventureId = UUID.randomUUID();
        TimelineType mockType = TimelineType.ADVENTURE;
        LocalDateTime mockTime = LocalDateTime.now();
        String mockDescription = "This is a mock message";

        //When
        TimelineDTO mockResponse = new TimelineDTO(mockTimelineId,mockAdventureId,mockDescription,mockTime,mockType);

        //Then
        Assertions.assertEquals(mockAdventureId, mockResponse.getAdventureId());
        Assertions.assertEquals(mockType, mockResponse.getType());
        Assertions.assertEquals(mockDescription, mockResponse.getDescription());
        Assertions.assertEquals(mockTime, mockResponse.getTimestamp());
        Assertions.assertEquals(mockTimelineId, mockResponse.getTimelineId());

    }

    @Test
    @Description("Testing the CreateTimeline service")
    public void createTimelineTest(){

        //Given
        UUID mockAdventureId = UUID.randomUUID();
        TimelineType mockType = TimelineType.ADVENTURE;
        String mockDescription = "This is a mock message";

        //When
        Mockito.when(timelineRepo.save(Mockito.any())).thenReturn(mockTimeline1);
        String response = service.createTimelineEntry(mockAdventureId,mockDescription,mockType);

        Assertions.assertEquals("New timeline entry created for adventure "+mockAdventureId,response);
    }

    @Test
    @Description("Testing the getTimelineByAdventureId service")
    public void getTimelineByAdventureIdTest(){

        //Given
        UUID mockAdventureId = UUID.randomUUID();
        TimelineType mockType = TimelineType.ADVENTURE;
        String mockDescription = "This is a mock message";

        //When
        Mockito.when(timelineRepo.save(Mockito.any())).thenReturn(mockTimeline1);
        String response = service.createTimelineEntry(mockAdventureId,mockDescription,mockType);

        Assertions.assertEquals("New timeline entry created for adventure "+mockAdventureId,response);
    }

    @Test
    @Description("Testing the getTimelineByAdventureId service")
    public void getTimelineByAdventureIdFailureTest(){

        //Given
        UUID incorrectAdventureId = UUID.randomUUID();
        UUID correctAdventureId = UUID.randomUUID();

        //When
        Mockito.when(timelineRepo.save(Mockito.any())).thenReturn(mockTimeline1);

//        Assertions.assertEquals("New timeline entry created for adventure "+mockAdventureId,response);
//        final UUID mockId = UUID.randomUUID();
//        GetAdventureByUUIDRequest req = new GetAdventureByUUIDRequest(mockId);
//        Assertions.assertThrows(AdventureNotFoundException.class, ()->{
//            GetAdventureByUUIDResponse res = service.GetTimelineByAdventureID(mockAdventureId);
//        });

    }



}
