package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashBoardController  {

    @Autowired
    private EmployeeDao employeeDao;

    /*//count designation
    @RequestMapping(value = "/employee/designationcountbyemployeedata", method = RequestMethod.GET, produces = "application/json")
    public Integer[] employeecountbydesignationdata() {
        return employeeDao.countEmployeesByDesignationId();
    }*/

}
