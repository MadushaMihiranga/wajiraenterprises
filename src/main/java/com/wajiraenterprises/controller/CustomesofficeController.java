package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.CustomesofficeDao;
import com.wajiraenterprises.entity.Customesoffice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomesofficeController {

    @Autowired
    private CustomesofficeDao dao;

    @RequestMapping(value = "/customesoffices/list", method = RequestMethod.GET, produces = "application/json")
    public List<Customesoffice> customesoffices() { return dao.list(); }

}
