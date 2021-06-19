package com.adventureit.notificationservice;

import com.adventureit.notificationservice.Entity.Notification;
import com.adventureit.notificationservice.Repos.NotificationRepository;
import com.adventureit.notificationservice.Requests.CreateNotificationRequest;
import com.adventureit.notificationservice.Requests.RetrieveNotificationRequest;
import com.adventureit.notificationservice.Requests.SendEmailNotificationRequest;
import com.adventureit.notificationservice.Requests.SendEmailRequest;
import com.adventureit.notificationservice.Responses.CreateNotificationResponse;
import com.adventureit.notificationservice.Responses.SendEmailNotificationResponse;
import com.adventureit.notificationservice.Service.notificationService;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RetrieveNotificationJunitTest {

    @Mock
    private NotificationRepository mockNotificationRepository;


    @Mock
    private JavaMailSender mail;

    private notificationService notificationSUT = new notificationService(mail, mockNotificationRepository);

    Date date1 = new Date();


    String mockEmail = "u19024143@gmail.com";

    String mockSubject1 = "Test Subject";

    String mockMessage1 = "Test message 1";
    String mockMessage2 = "Test message 2";
    String mockMessage3 = "Test message 3";

    String userId1S = "9d2a50a0-3648-41f2-b344-08a4459a7f27";
    String userId2S = "1e9c9cef-6587-449c-8e02-b765fd9a63ae";
    String userId3S = "191d1285-7d7e-4904-b918-8f58b4c24631";

    UUID userId1U = UUID.fromString("9d2a50a0-3648-41f2-b344-08a4459a7f27");
    UUID userId2U = UUID.fromString("1e9c9cef-6587-449c-8e02-b765fd9a63ae");
    UUID userId3U = UUID.fromString("191d1285-7d7e-4904-b918-8f58b4c24631");

    UUID notificationId1U = UUID.fromString("6042edfd-a908-4364-a5ef-69f060bdf3da");
    UUID notificationId2U = UUID.fromString("d1657bab-3509-417f-ae8a-956b1b2cad38");
    UUID notificationId3U = UUID.fromString("79b608e7-d303-4ef4-a5ea-b88cce924619");

    Notification note1 = new Notification(notificationId1U,userId1U,mockMessage1,date1,null);
    Notification note4 = new Notification(notificationId1U,userId1U,mockMessage1,date1,date1);

    @BeforeEach
    void setup() {
        List<Notification> list1 = new ArrayList<>();
        List<Notification> list2 = new ArrayList<>();
        List<Notification> list3 = new ArrayList<>();
        List<Notification> list4 = new ArrayList<>();
        List<Notification> list5 = new ArrayList<>();
        List<Notification> list6 = new ArrayList<>();


        Notification note2 = new Notification(notificationId2U,userId2U,mockMessage2,date1,null);
        Notification note3 = new Notification(notificationId3U,userId3U,mockMessage3,date1,null);
        Notification note5 = new Notification(notificationId2U,userId2U,mockMessage2,date1,date1);
        Notification note6 = new Notification(notificationId3U,userId3U,mockMessage3,date1,date1);

        //doReturn(list4).when(mockNotificationRepository).getNotificationByUserID(notificationId2U);
        //doReturn(list6).when(mockNotificationRepository).getNotificationByUserID(notificationId3U);
        //doReturn(list1).when(mockNotificationRepository).getNotificationByUserIDAndReadDateTime(notificationId1U,null);
        //doReturn(list3).when(mockNotificationRepository).getNotificationByUserIDAndReadDateTime(notificationId2U,null);
        //doReturn(list5).when(mockNotificationRepository).getNotificationByUserIDAndReadDateTime(notificationId3U,null);
    }


    @Test
    public void testRetrieveNotificationRequestObjectS(){
        RetrieveNotificationRequest testRequest = new RetrieveNotificationRequest(userId1S, true);
        assertNotNull(testRequest);
        assertEquals(userId1S,testRequest.getUserId_S());
        assertEquals(userId1U,testRequest.getUserId_U());
        assertEquals(true,testRequest.isUnreadOnly());
    }

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
        Mockito.when(mockNotificationRepository.getNotificationByUserID(notificationId1U)).thenReturn(List.of(note1, note4));

        RetrieveNotificationRequest testRequest = new RetrieveNotificationRequest(userId1S, false);
        List<Notification> list = notificationSUT.retrieveNotifications(testRequest);
        verify(mockNotificationRepository).getNotificationByUserID(userId1U);
    }


    @Test
    public void testRetrieveNotificationServiceGetUnreadNotifications(){
        RetrieveNotificationRequest testRequest = new RetrieveNotificationRequest(userId1S, true);
        List<Notification> list = notificationSUT.retrieveNotifications(testRequest);
        verify(mockNotificationRepository).getNotificationByUserIDAndReadDateTime(userId1U,null);
        verify(mockNotificationRepository).removeAllByUserIDAndReadDateTime(userId1U,null);
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
