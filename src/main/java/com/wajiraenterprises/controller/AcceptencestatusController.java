package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.AcceptencestatusDao;
import com.wajiraenterprises.entity.Acceptencestatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AcceptencestatusController {

    @Autowired
    private AcceptencestatusDao dao;

    @RequestMapping(value = "/acceptencestatus/list", method = RequestMethod.GET, produces = "application/json")
    public List<Acceptencestatus> acceptencestatuses() { return dao.list(); }

}
