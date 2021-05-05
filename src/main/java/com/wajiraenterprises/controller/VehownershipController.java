package com.wajiraenterprises.controller;


import com.wajiraenterprises.dao.VehownershipDao;
import com.wajiraenterprises.entity.Vehownership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VehownershipController {

    @Autowired
    private VehownershipDao dao;

    @RequestMapping(value = "/vehownership/list", method = RequestMethod.GET, produces = "application/json")
    public List<Vehownership> vehownership() { return dao.list(); }

}
