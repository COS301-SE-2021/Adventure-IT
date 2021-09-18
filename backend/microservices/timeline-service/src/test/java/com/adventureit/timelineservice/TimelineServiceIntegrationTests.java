package com.adventureit.timelineservice;

import com.adventureit.timelineservice.entity.Timeline;
import com.adventureit.shareddtos.timeline.TimelineType;
import com.adventureit.timelineservice.repository.TimelineRepository;
import com.adventureit.shareddtos.timeline.requests.CreateTimelineRequest;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"service-registry-client.instance.hostname=localhost","service-registry-client.client.service-url.defaultZone=http://localhost:8761/eureka/","service-registry-client.client.register-with-eureka=true", "service-registry-client.client.fetch-registry=true","timeline-microservice.application-name=TIMELINE-MICROSERVICE", "timeline-microservice.datasource.url=jdbc:postgresql://adventure-it-db.c9gozrkqo8dv.us-east-2.rds.amazonaws.com/adventureit?socketTimeout=5","timeline-microservice.datasource.username=postgres","timeline-microservice.datasource.password=310PB!Gq%f&J","timeline-microservice.datasource.hikari.maximum-pool-size=2","timeline-microservice.jpa.hibernate.ddl-auto=update","timeline-microservice.jpa.show-sql=false","timeline-microservice.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect","timeline-microservice.jpa.properties.hibernate.format_sql=true" })
class TimelineServiceIntegrationTests {

    @Autowired
    private TimelineRepository timelineRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;



    @Test
    @Description("Ensure that the view function works")
    void createTimelineTest(){
        UUID adventureId = UUID.randomUUID();
        String description = "Test description";
        TimelineType type = TimelineType.BUDGET;
        CreateTimelineRequest req = new CreateTimelineRequest(adventureId,type,description);
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/timeline/createTimeline/",req, String.class);
        Assertions.assertEquals("New timeline entry created for adventure "+adventureId,response);
    }


    @Test
    @Description("Ensure that the view function works")
    void getTimelineByAdventureIDTest(){
        UUID timelineId = UUID.randomUUID();
        UUID adventureId = UUID.randomUUID();
        String description = "Test description";
        LocalDateTime date = LocalDateTime.now();
        TimelineType type = TimelineType.BUDGET;
        Timeline timeline = new Timeline(timelineId,adventureId,description,date,type);
        timelineRepository.saveAndFlush(timeline);
        List response = this.restTemplate.getForObject("http://localhost:" + port + "/timeline/getTimelineByAdventure/"+adventureId, List.class);
        Assertions.assertEquals(1,response.size());
    }

    @Test
    @Description("Ensure that the view function works")
    void deleteTimelineByAdventureIDTest(){
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


}
