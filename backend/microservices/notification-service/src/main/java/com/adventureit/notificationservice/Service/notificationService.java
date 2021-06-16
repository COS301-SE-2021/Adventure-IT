package com.adventureit.notificationservice.Service;


import com.adventureit.notificationservice.Entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Properties;
import java.util.UUID;


@Service
public class notificationService {
    private JavaMailSender mailSender;

    @Autowired
    public notificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
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


    }
}
