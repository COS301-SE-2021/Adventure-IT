package com.adventureit.notificationservice;

import com.adventureit.notificationservice.entity.Notification;
import com.adventureit.notificationservice.repos.NotificationRepository;
import com.adventureit.shareddtos.notification.requests.CreateNotificationRequest;
import com.adventureit.shareddtos.notification.requests.RetrieveNotificationRequest;
import com.adventureit.shareddtos.notification.requests.SendEmailNotificationRequest;
import com.adventureit.shareddtos.notification.requests.SendEmailRequest;
import com.adventureit.notificationservice.responses.CreateNotificationResponse;
import com.adventureit.notificationservice.responses.SendEmailNotificationResponse;
import com.adventureit.notificationservice.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



class NotificationUnitTest {


    private final NotificationRepository mockNotificationRepository = Mockito.mock(NotificationRepository.class);

    private final NotificationService notificationSUT = new NotificationService( mockNotificationRepository);

    Date date1 = new Date();


    String mockEmail = "u19024143@gmail.com";

    String mockSubject1 = "Test Subject";

    String mockMessage1 = "Test message 1";
    String mockMessage3 = "Test message 3";

    String userId1S = "9d2a50a0-3648-41f2-b344-08a4459a7f27";
    UUID userId1U = UUID.fromString("9d2a50a0-3648-41f2-b344-08a4459a7f27");
    UUID notificationId1U = UUID.fromString("6042edfd-a908-4364-a5ef-69f060bdf3da");
    Notification note1 = new Notification(notificationId1U,userId1U,mockMessage1,date1,null);
    Notification note4 = new Notification(notificationId1U,userId1U,mockMessage1,date1,date1);



    @Test
     void testRetrieveNotificationRequestObjectS(){
        RetrieveNotificationRequest testRequest = new RetrieveNotificationRequest(userId1S, true);
        assertNotNull(testRequest);
        assertEquals(userId1S,testRequest.getUserIdS());
        assertEquals(userId1U,testRequest.getUserIdU());
        assertTrue(testRequest.isUnreadOnly());
    }

    @Test
     void testRetrieveNotificationRequestObjectU(){
        RetrieveNotificationRequest testRequest = new RetrieveNotificationRequest(userId1U, true);
        assertNotNull(testRequest);
        assertEquals(userId1S,testRequest.getUserIdS());
        assertEquals(userId1U,testRequest.getUserIdU());
        assertTrue(testRequest.isUnreadOnly());
    }

    @Test
     void testCreateNotificationResponseObject(){
        CreateNotificationResponse testResponse = new CreateNotificationResponse(mockMessage3,true);
        assertNotNull(testResponse);
        assertEquals(mockMessage3,testResponse.getResponseMessage());
        assertTrue(testResponse.isSuccess());
    }

    @Test
     void testSendEmailNotificationResponseObject(){
        SendEmailNotificationResponse testResponse = new SendEmailNotificationResponse(true,mockMessage3);
        assertNotNull(testResponse);
        assertEquals(mockMessage3,testResponse.getReturnMessage());
        assertTrue(testResponse.isSuccess());
    }



    @Test
     void testCreateNotificationRequestObject(){
       CreateNotificationRequest testRequest = new CreateNotificationRequest(userId1U,mockMessage1);
        assertNotNull(testRequest);
        assertEquals(userId1U,testRequest.getUserId());
        assertEquals(mockMessage1,testRequest.getMessage());
    }

    @Test
     void testSendEmailNotificationRequestObject(){
        SendEmailNotificationRequest testRequest = new SendEmailNotificationRequest(userId1U,mockSubject1,mockMessage1,"testemail@mail.com");
        assertNotNull(testRequest);
        assertEquals(userId1U,testRequest.getUserId());
        assertEquals(mockSubject1,testRequest.getSubject());
        assertEquals(mockMessage1,testRequest.getBody());
    }

    @Test
     void testSendEmailRequestObject(){
        SendEmailRequest testRequest = new SendEmailRequest(mockEmail,mockSubject1,mockMessage1);
        assertNotNull(testRequest);
        assertEquals(mockEmail,testRequest.getEmail());
        assertEquals(mockSubject1,testRequest.getSubject());
        assertEquals(mockMessage1,testRequest.getBody());
    }

    @Test
     void testNotificationEntity(){
        Notification mockNote = new Notification(notificationId1U,userId1U,mockMessage1,date1,null);
        assertEquals(notificationId1U,mockNote.getNotificationID());
        assertEquals(userId1U,mockNote.getUserID());
        assertEquals(mockMessage1,mockNote.getPayload());
        assertEquals(date1,mockNote.getCreatedDateTime());
        assertNull(mockNote.getReadDateTime());
    }


    @Test
     void testRetrieveNotificationServiceGetAllNotifications(){
        Mockito.when(mockNotificationRepository.getNotificationByUserID(userId1U)).thenReturn(List.of(note1, note4));
        RetrieveNotificationRequest testRequest = new RetrieveNotificationRequest(userId1U, false);
        List<Notification> list = notificationSUT.retrieveNotifications(testRequest);
        assertEquals(2,list.size());
    }


    @Test
     void testRetrieveNotificationServiceGetUnreadNotifications(){
        Mockito.when(mockNotificationRepository.getNotificationByUserIDAndReadDateTime(userId1U,null)).thenReturn(List.of(note1));
        RetrieveNotificationRequest testRequest = new RetrieveNotificationRequest(userId1U, true);
        List<Notification> list = notificationSUT.retrieveNotifications(testRequest);
        verify(mockNotificationRepository).getNotificationByUserIDAndReadDateTime(userId1U,null);
        verify(mockNotificationRepository).removeAllByUserIDAndReadDateTime(userId1U,null);
        assertEquals(1,list.size());
    }

    @Test
     void testCreateNotificationService(){
        CreateNotificationRequest testRequest = new CreateNotificationRequest(userId1U,mockMessage1);
        CreateNotificationResponse testResponse = notificationSUT.createNotification(testRequest);
        assertNotNull(testResponse);
        assertEquals("Notification saved for user no. "+userId1U,testResponse.getResponseMessage());
        assertTrue(testResponse.isSuccess());
    }

    @Test
     void testSendEmailNotificationService(){
        SendEmailNotificationRequest testRequest = new SendEmailNotificationRequest(userId1U,mockSubject1,mockMessage1,"testemail@mail.com");
        SendEmailNotificationResponse testResponse = notificationSUT.sendEmailNotification(testRequest);
        assertNotNull(testResponse);
        assertEquals("Email sent to user no. "+userId1U,testResponse.getReturnMessage());
        assertTrue(testResponse.isSuccess());
    }







}
