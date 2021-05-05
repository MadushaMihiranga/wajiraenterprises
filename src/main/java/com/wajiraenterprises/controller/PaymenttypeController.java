package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.PaymenttypeDao;
import com.wajiraenterprises.entity.Paymenttype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymenttypeController {

    @Autowired
    private PaymenttypeDao dao;

    @RequestMapping(value = "/paymenttype/list", method = RequestMethod.GET, produces = "application/json")
    public List<Paymenttype> list(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return dao.list();
        }
        return null;
    }


}
