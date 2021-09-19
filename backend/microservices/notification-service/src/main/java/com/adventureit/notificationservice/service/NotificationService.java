package com.adventureit.notificationservice.service;


import com.adventureit.notificationservice.entity.Notification;
import com.adventureit.notificationservice.entity.NotificationUser;
import com.adventureit.notificationservice.repos.NotificationRepository;
import com.adventureit.notificationservice.repos.NotificationUserRepository;
import com.adventureit.shareddtos.notification.requests.*;
import com.adventureit.notificationservice.responses.CreateNotificationResponse;
import com.adventureit.notificationservice.responses.SendEmailNotificationResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class NotificationService {
    private final JavaMailSender mailSender;
    private final NotificationRepository repo;
    private final NotificationUserRepository notificationUserRepository;
    private final FirebaseMessaging firebaseMessaging;

    public NotificationService(JavaMailSender mailSender, NotificationRepository repo, NotificationUserRepository notificationUserRepository, FirebaseMessaging firebaseMessaging) {
        this.mailSender = mailSender;
        this.repo = repo;
        this.firebaseMessaging = firebaseMessaging;
        this.notificationUserRepository = notificationUserRepository;
    }

    public void sendEmail(String email,String subject,String message){
        Properties props =new Properties();
        props.put("mail.smtp.ssl.trust", "*");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    public CreateNotificationResponse createNotification(CreateNotificationRequest req){
        Date currentDate = new Date();
        UUID notificationId = UUID.randomUUID();
        UUID userid = req.getUserId();
        String message = req.getMessage();
        Notification newNote = new Notification(notificationId,userid,message,currentDate,null);
        repo.save(newNote);
        return new CreateNotificationResponse("Notification saved for user no. "+userid,true);

    }
    public SendEmailNotificationResponse sendEmailNotification(SendEmailNotificationRequest req){
        UUID userID = req.getUserId();
        String email = req.getEmail();
        String subject = req.getSubject();
        String body = req.getBody();
        sendEmail(email,subject,body);
        return new SendEmailNotificationResponse(true,"Email sent to user no. "+userID);
    }

    public List<Notification> retrieveNotifications(RetrieveNotificationRequest req){
        Date currentDate = new Date();
        boolean unreadOnly = req.isUnreadOnly();
        UUID userID =req.getUserIdU();
        if(unreadOnly){
            List<Notification> list = repo.getNotificationByUserIDAndReadDateTime(userID,null);
            for (Notification notification : list) {
                notification.setReadDateTime(currentDate);
            }
            repo.removeAllByUserIDAndReadDateTime(userID,null);
            repo.saveAll(list);
            return list;
        }else{
            return repo.getNotificationByUserID(userID);
        }
    }

    public String sendFirebaseNotification(SendFirebaseNotificationRequest req){
        NotificationUser user = notificationUserRepository.findNotificationUserByUserId(req.getUserId());
        if(user == null){
            return "User not found";
        }

        com.google.firebase.messaging.Notification notification = com.google.firebase.messaging.Notification
                .builder()
                .setTitle(req.getTitle())
                .setBody(req.getBody())
                .build();

        Message message = Message
                .builder()
                .setToken(user.getFirebaseToken())
                .setNotification(notification)
                .putAllData(req.getData())
                .build();
        try {
            return firebaseMessaging.send(message);
        }
        catch(FirebaseMessagingException e){
            return e.toString();
        }
    }

    public String addFirebaseUser(FirebaseUserRequest req) {
        NotificationUser foundUser = notificationUserRepository.findNotificationUserByUserId(req.getUserId());
        if(foundUser == null){
            // create new user
            NotificationUser newUser = new NotificationUser(req.getUserId(), req.getFirebaseToken());
            notificationUserRepository.save(newUser);
            return "Added";
        }
        else {
            // update existing user
            foundUser.setFirebaseToken(req.getFirebaseToken());
            notificationUserRepository.save(foundUser);
            return "Updated";
        }
    }

    public String sendFirebaseNotifications(SendFirebaseNotificationsRequest req) {
        List<UUID> users = new ArrayList<>();
        for(UUID userId : req.getUserIds()){
            NotificationUser user = notificationUserRepository.findNotificationUserByUserId(userId);
            if(user == null){
               break;
            }

            com.google.firebase.messaging.Notification notification = com.google.firebase.messaging.Notification
                    .builder()
                    .setTitle(req.getTitle())
                    .setBody(req.getBody())
                    .build();

            Message message = Message
                    .builder()
                    .setToken(user.getFirebaseToken())
                    .setNotification(notification)
                    .putAllData(req.getData())
                    .build();
            try {
                firebaseMessaging.send(message);
            }
            catch(FirebaseMessagingException e){
                return e.toString();
            }
        }
        return "All notifications sent";
    }
}
