package com.adventureit.timelineservice;

import com.adventureit.timelineservice.Controller.TimelineController;
import com.adventureit.timelineservice.Entity.Timeline;
import com.adventureit.timelineservice.Entity.TimelineType;
import com.adventureit.timelineservice.Repository.TimelineRepository;
import com.adventureit.timelineservice.Requests.CreateTimelineRequest;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TimelineServiceIntegrationTests {

    @Autowired
    private TimelineRepository timelineRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    @Description("Ensure that the view function works")
    public void getTimelineByAdventureIDTest(){
        UUID timelineId = UUID.randomUUID();
        UUID adventureId = UUID.randomUUID();
        String description = "Test description";
        LocalDateTime date = LocalDateTime.now();
        TimelineType type = TimelineType.BUDGET;
        Timeline timeline = new Timeline(timelineId,adventureId,description,date,type);
        timelineRepository.saveAndFlush(timeline);
        List response = this.restTemplate.getForObject("http://localhost:" + port + "/timeline/getTimelineByAdventure/"+adventureId, List.class);
        Assertions.assertEquals(response.size(),1);
    }

    @Test
    @Description("Ensure that the view function works")
    public void deleteTimelineByAdventureIDTest(){
        UUID timelineId = UUID.randomUUID();
        UUID adventureId = UUID.randomUUID();
        String description = "Test description";
        LocalDateTime date = LocalDateTime.now();
        TimelineType type = TimelineType.BUDGET;
        Timeline timeline = new Timeline(timelineId,adventureId,description,date,type);
        timelineRepository.saveAndFlush(timeline);
        String response = this.restTemplate.getForObject("http://localhost:" + port + "/timeline/deleteTimelineByAdventureID/"+adventureId, String.class);
        Assertions.assertEquals("Timeline for adventure: "+adventureId+" has been deleted",response);
    }

    @Test
    @Description("Ensure that the view function works")
    public void createTimelineTest(){
        UUID adventureId = UUID.randomUUID();
        String description = "Test description";
        TimelineType type = TimelineType.BUDGET;
        CreateTimelineRequest req = new CreateTimelineRequest(adventureId,type,description);
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/timeline/createTimeline/",req, String.class);
        Assertions.assertEquals("New timeline entry created for adventure "+adventureId,response);
    }
}
