package com.adventureit.notificationservice.Service;


import com.adventureit.notificationservice.Entity.Notification;
import com.adventureit.notificationservice.Repos.NotificationRepository;
import com.adventureit.notificationservice.Requests.sendEmailNotificationRequest;
import com.adventureit.notificationservice.Responses.sendEmailNotificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;


@Service
public class notificationService {
    private JavaMailSender mailSender;
    private NotificationRepository repo;

    @Autowired
    public notificationService(JavaMailSender mailSender, NotificationRepository repo) {
        this.mailSender = mailSender;
        this.repo=repo;
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

    public void createNotification(){
        Date currentDate = new Date();

        UUID Notificationid = UUID.randomUUID();
        UUID Userid = UUID.randomUUID();
        Notification newNote = new Notification(Notificationid,Userid,"random message",currentDate,null);
        repo.save(newNote);

    }
    public sendEmailNotificationResponse sendEmailNotification(sendEmailNotificationRequest req){
        UUID userID = req.getUserId();
        String subject = req.getSubject();
        String body = req.getBody();



        return null;
    }

    public List<Notification> retrieveNotifications(UUID userID){
        return repo.getNotificationByNotificationIDAndReadDateTime(userID,null);
    }
}
