package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.DeliverytermsDao;
import com.wajiraenterprises.entity.Deliveryterms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeliverytermsController {

    @Autowired
    private DeliverytermsDao dao;

    @RequestMapping(value = "/deliveryterms/list", method = RequestMethod.GET, produces = "application/json")
    public List<Deliveryterms> deliveryterms() { return dao.list(); }


}
