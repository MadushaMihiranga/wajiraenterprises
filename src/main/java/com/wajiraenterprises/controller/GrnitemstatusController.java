package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.GrnitemstatusDao;
import com.wajiraenterprises.entity.Grnitemstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GrnitemstatusController {

    @Autowired
    private GrnitemstatusDao dao;

    @RequestMapping(value = "/grnitemstatus/list", method = RequestMethod.GET, produces = "application/json")
    public List<Grnitemstatus> grnitemstatuses() { return dao.list(); }

}
