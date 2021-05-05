package com.wajiraenterprises.controller;

import com.wajiraenterprises.entity.Packagetype;
import com.wajiraenterprises.services.EmailService;
import com.wajiraenterprises.services.PasswordResetHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PasswordResetHandlerController {

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/passwordchange/emailconfirmation",method = RequestMethod.GET, produces = "application/json")
    public String  packagetypes(){

        System.out.println("REST PASSWORD");
        String message = PasswordResetHandler.generatePin();
        System.out.println(PasswordResetHandler.generatePin());
        emailService.sendStudentEmail("madusha19970126@gmail.com","Rest Password\n",message+" is your pin number to reset user password");
        return PasswordResetHandler.generatePin();
        //return dao.list();
    }

}
