package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.PestatusDao;
import com.wajiraenterprises.entity.Pestatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PestatusController {

    @Autowired
    private PestatusDao dao;

    @RequestMapping(value = "/pestatus/list", method = RequestMethod.GET, produces = "application/json")
    public List<Pestatus> status() {
        return dao.list();
    }

    @RequestMapping(value = "/getstatus", method = RequestMethod.GET,params = "name",produces = "application/json")
    public Pestatus clearingprocess(@RequestParam("name") String name) {
        Pestatus pestatus;
        pestatus = dao.findByName(name);
        //System.out.println("Status : "+pestatus.getName());
        return pestatus;
    }





}
