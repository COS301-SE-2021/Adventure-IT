package com.adventureit.notificationservice.Service;

import com.adventureit.notificationservice.Config.EmailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Properties;


@Service
public class notificationService {
    private EmailConfig emailConf;

    private JavaMailSender mailSender;

    @Autowired
    public notificationService(EmailConfig emailConf, JavaMailSender mailSender) {
        this.emailConf = emailConf;
        this.mailSender = mailSender;
    }

    public void sendEmail(String email,String subject,String message){
        Properties props =new Properties();
        props.put("mail.smtp.ssl.trust", "*");
        /*mailSender.setPort(Integer.parseInt(emailConf.getPort()));
        mailSender.setHost(emailConf.getHost());
        mailSender.setPassword(emailConf.getPassword());
        mailSender.setUsername(emailConf.getUsername());*/

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
}
