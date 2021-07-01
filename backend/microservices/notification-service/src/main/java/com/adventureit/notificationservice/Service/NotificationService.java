package com.adventureit.notificationservice.Service;


import com.adventureit.notificationservice.Entity.Notification;
import com.adventureit.notificationservice.Repos.NotificationRepository;
import com.adventureit.notificationservice.Requests.CreateNotificationRequest;
import com.adventureit.notificationservice.Requests.RetrieveNotificationRequest;
import com.adventureit.notificationservice.Requests.SendEmailNotificationRequest;
import com.adventureit.notificationservice.Responses.CreateNotificationResponse;
import com.adventureit.notificationservice.Responses.SendEmailNotificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;


@Service
public class NotificationService {
    private final JavaMailSender mailSender;
    private final NotificationRepository repo;


    public NotificationService(JavaMailSender mailSender, NotificationRepository repo) {
        this.mailSender = mailSender;
        this.repo = repo;
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
        String email = "kevin9716cui@gmail.com";
        String subject = req.getSubject();
        String body = req.getBody();
        sendEmail(email,subject,body);
        return new SendEmailNotificationResponse(true,"Email sent to user no. "+userID);
    }

    public List<Notification> retrieveNotifications(RetrieveNotificationRequest req){
        Date currentDate = new Date();
        boolean unread_only = req.isUnreadOnly();
        UUID userID =req.getUserId_U();
        //UUID userID = UUID.fromString("9d2a50a0-3648-41f2-b344-08a4459a7f27");
        if(unread_only){
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
}
