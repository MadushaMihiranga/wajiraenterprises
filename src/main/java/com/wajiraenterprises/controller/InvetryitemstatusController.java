package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.InvetryitemstatusDao;
import com.wajiraenterprises.entity.Invetryitemstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InvetryitemstatusController {

    @Autowired
    private InvetryitemstatusDao dao;

    @RequestMapping(value = "/inventryitemstatus/list", method = RequestMethod.GET, produces = "application/json")
    public List<Invetryitemstatus> invetryitemstatuses() {
        return dao.list();
    }

}
