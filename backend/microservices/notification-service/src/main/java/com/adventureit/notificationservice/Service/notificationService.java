package com.adventureit.notificationservice.Service;

import com.adventureit.notificationservice.Config.EmailConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;


@Service
public class notificationService {
    private EmailConfig emailConf;
    private JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    @Autowired
    public notificationService(EmailConfig emailConf) {
        this.emailConf = emailConf;
    }

    public void sendEmail(@JsonProperty("email") String email, @JsonProperty("subject")String subject, @JsonProperty("message")String message){
        mailSender.setPort(Integer.parseInt(emailConf.getPort()));
        mailSender.setHost(emailConf.getHost());
        mailSender.setPassword(emailConf.getPassword());
        mailSender.setUsername(emailConf.getUsername());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("kevin9716cui@gmail.com");
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
