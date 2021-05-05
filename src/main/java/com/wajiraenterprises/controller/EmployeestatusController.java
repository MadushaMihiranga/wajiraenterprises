package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.EmployeestatusDao;
import com.wajiraenterprises.entity.Employeestatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeestatusController {

    @Autowired
    private EmployeestatusDao dao;

    @RequestMapping(value = "/employeestatuses/list", method = RequestMethod.GET, produces = "application/json")
    public List<Employeestatus> employeestatuses() {
        return dao.list();
    }


}
