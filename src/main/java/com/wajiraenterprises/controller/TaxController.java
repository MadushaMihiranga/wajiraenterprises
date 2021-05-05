package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.TaxDao;
import com.wajiraenterprises.entity.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaxController {

    @Autowired
    private TaxDao taxDao;

    @RequestMapping(value = "/tax/list", method = RequestMethod.GET, produces = "application/json")
    public List<Tax> taxs() { return taxDao.list(); }

}
