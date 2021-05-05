package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.DeliverystatusDao;
import com.wajiraenterprises.entity.Deliverystatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeliverystatusController {

    @Autowired
    private DeliverystatusDao dao;

    @RequestMapping(value = "/deliverystatus/list", method = RequestMethod.GET, produces = "application/json")
    public List<Deliverystatus> deliverystatuses() {
        return dao.list();
    }

}
