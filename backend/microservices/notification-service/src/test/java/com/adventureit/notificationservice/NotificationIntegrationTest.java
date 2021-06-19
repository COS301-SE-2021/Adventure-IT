package com.adventureit.notificationservice;

import com.adventureit.notificationservice.Repos.NotificationRepository;
import com.adventureit.userservice.Entities.User;
import org.mockito.Mock;

import java.util.UUID;

public class NotificationIntegrationTest {


    @Mock
    NotificationRepository mockRepo;

    @Mock
    UserRepository mockUserRepo;

    UUID userId1U = UUID.fromString("9d2a50a0-3648-41f2-b344-08a4459a7f27");
    String userName1 = "User1";
    String userlName1 = "Surname1";
    String validEmail = "u19024143@tuks.co.za";
    String validPassword = "ValidPass123!";
    String validPhoneNum = "0794083124";

    User validUser1 = new User( userId1U,userName1,userlName1, validEmail,validPassword,validPhoneNum);


}
