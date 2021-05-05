package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.DeliveryrequeststatusDao;
import com.wajiraenterprises.entity.Deliveryrequeststatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeliveryrequeststatusController {

    @Autowired
    private DeliveryrequeststatusDao dao;

    @RequestMapping(value = "/delrequeststatus/list", method = RequestMethod.GET, produces = "application/json")
    public List<Deliveryrequeststatus> status() { return dao.list(); }


}
