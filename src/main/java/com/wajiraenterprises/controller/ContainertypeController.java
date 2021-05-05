package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.ContainertypeDao;
import com.wajiraenterprises.entity.Containertype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContainertypeController {

    @Autowired
    private ContainertypeDao dao;

    @RequestMapping(value = "/containertype/list", method = RequestMethod.GET, produces = "application/json")
    public List<Containertype> containertype() { return dao.list(); }

}
