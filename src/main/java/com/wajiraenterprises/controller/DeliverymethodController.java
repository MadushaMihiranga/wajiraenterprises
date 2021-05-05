package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.DeliverymethodDao;
import com.wajiraenterprises.entity.Deliverymethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeliverymethodController {

    @Autowired
    private DeliverymethodDao deliverymethodDao;
    @RequestMapping(value = "/deliverymethod/list", method = RequestMethod.GET, produces = "application/json")
    public List<Deliverymethod> deliverymethod() { return deliverymethodDao.list(); }

}
