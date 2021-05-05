package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.TermsofpaymentDao;
import com.wajiraenterprises.entity.Termsofpayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TermsofPaymentController {

    @Autowired
    private TermsofpaymentDao dao;

    @RequestMapping(value = "/termsofpayment/list", method = RequestMethod.GET, produces = "application/json")
    public List<Termsofpayment> termsofpayments() { return dao.list(); }

}
