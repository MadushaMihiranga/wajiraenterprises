package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.ServicesDao;
import com.wajiraenterprises.entity.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServicesController {

    @Autowired
    private ServicesDao dao;

    @RequestMapping(value = "/servises/list", method = RequestMethod.GET, produces = "application/json")
    public List<Services> services() { return dao.list(); }


}
