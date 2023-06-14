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


    public void sendEmail(String to, String subject, Long userId) {

        String text = "Halo!!! Terima kasih sudah Melakukan pendaftaran akun pada aplikasi <b>Cashnote</b>!. " +
                "<br>Harap Konfirmasi melalui halaman ini : http://localhost:8081/users/" + userId + "/verify <br><br>" +
                "Jika ada pertanyaan yang lainnya silahkan reply email ini. <br><br>" +
                "Terima Kasih! ^_^";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("itsmyminipro@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
