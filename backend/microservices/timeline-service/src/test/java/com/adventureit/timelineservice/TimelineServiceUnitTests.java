package com.adventureit.timelineservice;


import com.adventureit.timelineservice.Entity.TimelineType;
import com.adventureit.timelineservice.Repository.TimelineRepository;
import com.adventureit.timelineservice.Service.TimelineServiceImplementation;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
public class TimelineServiceUnitTests {

    @Mock
    TimelineRepository timelineRepo;

    TimelineServiceImplementation service = new TimelineServiceImplementation(timelineRepo);

    /**
     * Testing Request Objects
     */

    @Test
    @Description("Testing to make sure that the CreateTimelineRequest returns the correct parameters that were passed in")
    public void createTimelineRequest(){
        //Given
        UUID mockAdventureId = UUID.randomUUID();
        TimelineType mockType = TimelineType.ADVENTURE;


    }
}
