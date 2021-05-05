package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.InvoicestatusDao;
import com.wajiraenterprises.entity.Invoicestatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InvoicestatusController {

    @Autowired
    private InvoicestatusDao dao;


    @RequestMapping(value = "/invoicestatus/list",method = RequestMethod.GET, produces = "application/json")
    public List<Invoicestatus> invoicestatuses(){return dao.list();}


}
