package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.ChequestatusDao;
import com.wajiraenterprises.entity.Chequestatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChequestatusController {
    @Autowired
    private ChequestatusDao dao;


    @RequestMapping(value = "/chequestatus/list", method = RequestMethod.GET, produces = "application/json")
    public List<Chequestatus> chequestatuses() {
        return dao.list();
    }

}
