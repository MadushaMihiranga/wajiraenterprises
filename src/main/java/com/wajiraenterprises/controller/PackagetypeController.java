package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.PackagetypeDao;
import com.wajiraenterprises.entity.Packagetype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PackagetypeController {

    @Autowired
    private PackagetypeDao dao;

    @RequestMapping(value = "/packagetypes/list",method = RequestMethod.GET, produces = "application/json")
    public List<Packagetype> packagetypes(){return dao.list();}

}
