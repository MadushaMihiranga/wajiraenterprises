package com.wajiraenterprises.services;

import com.wajiraenterprises.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


import java.security.SecureRandom;

@RestController
public class PasswordResetHandler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserDao userDao;

    public static String generatePin(){
        SecureRandom random = new SecureRandom();
        Integer num = random.nextInt(100000);
        return String.format("%05d", num);
    }

    public void sendPin(){

        String message;
        String email;
        String user;

        System.out.println(generatePin());


       // emailService.sendStudentEmail("madusha19970126@gmail.com","Welcome to Karunadasa Learners",message);

    }




}
