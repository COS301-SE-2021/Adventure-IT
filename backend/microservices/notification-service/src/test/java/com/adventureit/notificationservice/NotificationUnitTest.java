package com.adventureit.notificationservice;

import com.adventureit.notificationservice.Entity.Notification;
import com.adventureit.notificationservice.Repos.NotificationRepository;
import com.adventureit.notificationservice.Requests.CreateNotificationRequest;
import com.adventureit.notificationservice.Requests.RetrieveNotificationRequest;
import com.adventureit.notificationservice.Requests.SendEmailNotificationRequest;
import com.adventureit.notificationservice.Requests.SendEmailRequest;
import com.adventureit.notificationservice.Responses.CreateNotificationResponse;
import com.adventureit.notificationservice.Responses.SendEmailNotificationResponse;
import com.adventureit.notificationservice.Service.NotificationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;



public class NotificationUnitTest {

    @Mock
    private NotificationRepository mockNotificationRepository = Mockito.mock(NotificationRepository.class);


    @Mock
    private JavaMailSender mail = Mockito.mock(JavaMailSender.class);

    private NotificationService notificationSUT = new NotificationService(mail, mockNotificationRepository);

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
    public void testRetrieveNotificationRequestObjectU(){
        RetrieveNotificationRequest testRequest = new RetrieveNotificationRequest(userId1U, true);
        assertNotNull(testRequest);
        assertEquals(userId1S,testRequest.getUserId_S());
        assertEquals(userId1U,testRequest.getUserId_U());
        assertEquals(true,testRequest.isUnreadOnly());
    }

    @Test
    public void testCreateNotificationResponseObject(){
        CreateNotificationResponse testResponse = new CreateNotificationResponse(mockMessage3,true);
        assertNotNull(testResponse);
        assertEquals(mockMessage3,testResponse.getResponseMessage());
        assertEquals(true,testResponse.isSuccess());
    }

    @Test
    public void testSendEmailNotificationResponseObject(){
        SendEmailNotificationResponse testResponse = new SendEmailNotificationResponse(true,mockMessage3);
        assertNotNull(testResponse);
        assertEquals(mockMessage3,testResponse.getReturnmessage());
        assertEquals(true,testResponse.isSuccess());
    }



    @Test
    public void testCreateNotificationRequestObject(){
       CreateNotificationRequest testRequest = new CreateNotificationRequest(userId1U,mockMessage1);
        assertNotNull(testRequest);
        assertEquals(userId1U,testRequest.getUserId());
        assertEquals(mockMessage1,testRequest.getMessage());
    }

    @Test
    public void testSendEmailNotificationRequestObject(){
        SendEmailNotificationRequest testRequest = new SendEmailNotificationRequest(userId1U,mockSubject1,mockMessage1);
        assertNotNull(testRequest);
        assertEquals(userId1U,testRequest.getUserId());
        assertEquals(mockSubject1,testRequest.getSubject());
        assertEquals(mockMessage1,testRequest.getBody());
    }

    @Test
    public void testSendEmailRequestObject(){
        SendEmailRequest testRequest = new SendEmailRequest(mockEmail,mockSubject1,mockMessage1);
        assertNotNull(testRequest);
        assertEquals(mockEmail,testRequest.getEmail());
        assertEquals(mockSubject1,testRequest.getSubject());
        assertEquals(mockMessage1,testRequest.getBody());
    }

    @Test
    public void testNotificationEntity(){
        Notification mockNote = new Notification(notificationId1U,userId1U,mockMessage1,date1,null);
        assertEquals(notificationId1U,mockNote.getNotificationID());
        assertEquals(userId1U,mockNote.getUserID());
        assertEquals(mockMessage1,mockNote.getPayload());
        assertEquals(date1,mockNote.getCreatedDateTime());
        assertEquals(null,mockNote.getReadDateTime());
    }


    @Test
    public void testRetrieveNotificationServiceGetAllNotifications(){
        Mockito.when(mockNotificationRepository.getNotificationByUserID(userId1U)).thenReturn(List.of(note1, note4));
        RetrieveNotificationRequest testRequest = new RetrieveNotificationRequest(userId1U, false);
        List<Notification> list = notificationSUT.retrieveNotifications(testRequest);
        assertEquals(2,list.size());
    }


    @Test
    public void testRetrieveNotificationServiceGetUnreadNotifications(){
        Mockito.when(mockNotificationRepository.getNotificationByUserIDAndReadDateTime(userId1U,null)).thenReturn(List.of(note1));
        RetrieveNotificationRequest testRequest = new RetrieveNotificationRequest(userId1U, true);
        List<Notification> list = notificationSUT.retrieveNotifications(testRequest);
        verify(mockNotificationRepository).getNotificationByUserIDAndReadDateTime(userId1U,null);
        verify(mockNotificationRepository).removeAllByUserIDAndReadDateTime(userId1U,null);
        assertEquals(1,list.size());
    }

    @Test
    public void testCreateNotificationService(){
        CreateNotificationRequest testRequest = new CreateNotificationRequest(userId1U,mockMessage1);
        CreateNotificationResponse testResponse = notificationSUT.createNotification(testRequest);
        assertNotNull(testResponse);
        assertEquals("Notification saved for user no. "+userId1U,testResponse.getResponseMessage());
        assertEquals(true,testResponse.isSuccess());
    }

    @Test
    public void testSendEmailNotificationService(){
        SendEmailNotificationRequest testRequest = new SendEmailNotificationRequest(userId1U,mockSubject1,mockMessage1);
        SendEmailNotificationResponse testResponse = notificationSUT.sendEmailNotification(testRequest);
        assertNotNull(testResponse);
        assertEquals("Email sent to user no. "+userId1U,testResponse.getReturnmessage());
        assertEquals(true,testResponse.isSuccess());
    }







}
