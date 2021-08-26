package com.adventureit.timelineservice;

import com.adventureit.timelineservice.entity.Timeline;
import com.adventureit.timelineservice.entity.TimelineType;
import com.adventureit.timelineservice.exceptions.TimelineDoesNotExistException;
import com.adventureit.timelineservice.repository.TimelineRepository;
import com.adventureit.timelineservice.requests.CreateTimelineRequest;
import com.adventureit.timelineservice.responses.TimelineDTO;
import com.adventureit.timelineservice.service.TimelineServiceImplementation;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class TimelineServiceUnitTests {

    TimelineRepository timelineRepo = Mockito.mock(TimelineRepository.class);

    TimelineServiceImplementation service = new TimelineServiceImplementation(timelineRepo);

    UUID mockTimelineId1 = UUID.randomUUID();
    UUID mockAdventureId = UUID.randomUUID();
    TimelineType mockType = TimelineType.BUDGET;
    LocalDateTime mockTime = LocalDateTime.now();
    String mockDescription1 = "This is a mock timeline 1";


    Timeline mockTimeline1 = new Timeline(mockTimelineId1,mockAdventureId,mockDescription1,mockTime,mockType);

    /**
     * Testing Request Objects
     */

    @Test
    @Description("Testing to make sure that the CreateTimelineRequest returns the correct parameters that were passed in")
    void createTimelineRequest(){
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
    void timelineDtoTest(){
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
    void createTimelineTest(){

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
    void getTimelineByAdventureIdTest(){

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
    void getTimelineByAdventureIdFailureTest(){
        //Given
        UUID incorrectAdventureId = UUID.randomUUID();

        //When
        Mockito.when(timelineRepo.findAllByAdventureId(incorrectAdventureId)).thenReturn(null);
        Assertions.assertThrows(TimelineDoesNotExistException.class, ()->
                service.getTimelineByAdventureID(incorrectAdventureId));

    }

    @Test
    @Description("Testing the deletem,koTimelineByAdventureId service")
    void deleteTimelineByAdventureID(){
        //Given
        UUID correctAdventureId = UUID.randomUUID();

        //When
        Mockito.when(timelineRepo.findAllByAdventureId(correctAdventureId)).thenReturn(List.of(mockTimeline1));
        String response = service.deleteTimelineByAdventureID(correctAdventureId);
        //Then
        Assertions.assertEquals("Timeline for adventure: "+correctAdventureId+" has been deleted",response);
    }

    @Test
    @Description("Testing the getTimelineByAdventureId service")
    void deleteTimelineByAdventureIdFailureTest(){
        //Given
        UUID incorrectAdventureId = UUID.randomUUID();

        //When
        Mockito.when(timelineRepo.findAllByAdventureId(incorrectAdventureId)).thenReturn(null);
        Assertions.assertThrows(TimelineDoesNotExistException.class, ()->
                service.deleteTimelineByAdventureID(incorrectAdventureId));

    }

}
