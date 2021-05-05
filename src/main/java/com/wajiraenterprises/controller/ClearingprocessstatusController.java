package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.ClearingprocessstatusDao;
import com.wajiraenterprises.entity.Clearingprocess;
import com.wajiraenterprises.entity.Clearingprocessstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClearingprocessstatusController {

    @Autowired
    private ClearingprocessstatusDao dao;

    @RequestMapping(value = "/clearingprocessstatus/list", method = RequestMethod.GET, produces = "application/json")
    public List<Clearingprocessstatus> clearingprocessstatuses() {
        return dao.list();
    }

}
