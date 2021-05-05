package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.ServicesquotationDao;
import com.wajiraenterprises.entity.Servicesquotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServicesquotationController {
    @Autowired
    private ServicesquotationDao dao;

    @RequestMapping(value = "/servicequation/list", method = RequestMethod.GET, produces = "application/json")
    public List<Servicesquotation> servicesquotations() { return dao.list(); }

}
