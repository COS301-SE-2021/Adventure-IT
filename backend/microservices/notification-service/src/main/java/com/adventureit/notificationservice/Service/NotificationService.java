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
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;


@Service
public class NotificationService {
    private final JavaMailSenderImpl mailSender;
    private final NotificationRepository repo;

    @Autowired
    public NotificationService(JavaMailSenderImpl mailSender, NotificationRepository repo) {
        this.mailSender = mailSender;
        this.repo = repo;
    }

    /**
     *This use case will be used to send the email out to the recipient
     *It will mainly be used as a helper function to for the sendEmailNotification use case
     *
     *
     * @param email the email address of the person who the email is going to be sent to
     * @param subject The subject of the email
     * @param message The email body
     **/
    public void sendEmail(String email,String subject,String message){
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.starttls.enable","true");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    /**
     * This use case will be responsible for creating a notification for a specific user
     * @param req This is a CreateNotificationRequest object which will hold
     * @return it will simply return a response message as well as a response message
     **/

    public CreateNotificationResponse createNotification(CreateNotificationRequest req){
        Date currentDate = new Date();
        UUID notificationId = UUID.randomUUID();
        UUID userid = req.getUserId();
        String message = req.getMessage();
        Notification newNote = new Notification(notificationId,userid,message,currentDate,null);
        repo.save(newNote);
        return new CreateNotificationResponse("Notification saved for user no. "+userid,true);

    }

    /**
     * This use case is responsible for sending a email to a user.
     * @param req this request object hold the user Id of the user to whom the email must be sent to, as well as the Message subject
     *            and message body
     * @return The response object will simply hold a boolean to indicate whether the email was sent successfully as well as a return message
     */


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
