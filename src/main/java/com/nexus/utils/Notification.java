package com.nexus.utils;
import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;

@Service
public class Notification {

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String sender;


    public void sendEmail(String subject, String body) {
       new Thread(() -> {
            try {

                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(sender);
                message.setTo("karim.sayed091@gmail.com");
                message.setSubject(subject);
                message.setText(body);
                mailSender.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
       
    }

}
