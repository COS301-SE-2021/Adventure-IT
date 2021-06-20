package com.adventureit.notificationservice;

import com.adventureit.notificationservice.Entity.Notification;
import com.adventureit.notificationservice.Repos.NotificationRepository;
import com.adventureit.notificationservice.Requests.RetrieveNotificationRequest;
import com.adventureit.notificationservice.Service.NotificationService;
import com.adventureit.userservice.Entities.User;
import com.adventureit.userservice.Service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotificationIntegrationTest {





    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    NotificationService noteSUT;

    UUID userId1U = UUID.fromString("9d2a50a0-3648-41f2-b344-08a4459a7f27");
    String userName1 = "User1";
    String userlName1 = "Surname1";
    String validEmail = "u19024143@tuks.co.za";
    String validPassword = "ValidPass123!";
    String validPhoneNum = "0794083124";

    Date date1 = new Date();
    String mockMessage1 = "Test message 1";
    UUID notificationId1U = UUID.fromString("6042edfd-a908-4364-a5ef-69f060bdf3da");


    Notification note1 = new Notification(notificationId1U,userId1U,mockMessage1,date1,null);
    User validUser1 = new User( userId1U,userName1,userlName1, validEmail,validPassword,validPhoneNum);


    @Test
    public void httpTest_returnResponse(){
        //Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/notification/test", String.class),"Notification Controller is functional \n");

    }


}
