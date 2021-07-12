package com.movie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {


    @Autowired
    private Environment environment;

    @Autowired
    private JavaMailSender mailSender;


    public void sendRegisterMail(String email, String name,String token){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Kayıt");
        message.setText(buildRegisterText(name,token));
        mailSender.send(message);
    }

    public void sendPasswordMail(String email, String name,String token){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Şifre Yenileme");
        message.setText(buildPasswordText(name,token));
        mailSender.send(message);
    }

    private String buildRegisterText(String name,String token){
        return name+" Kayıt için tıklayın:  "+environment.getProperty("confirm.link")+token;
    }
    private String buildPasswordText(String name,String token){
        return name+" şifre yenileme için :  "+environment.getProperty("forgot.link")+token;
    }
}
