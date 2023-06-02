package com.minipro.cashnotes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;


    public void sendEmail(String to, String subject, UUID userId) {

        String text = "Harap Konfirmasi melalui halaman ini : http://localhost:8081/users/" + userId + "/verify";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("itsmyminipro@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
