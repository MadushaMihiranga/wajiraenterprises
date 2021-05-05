package com.wajiraenterprises.controller;


import com.wajiraenterprises.dao.TradeagreementDao;
import com.wajiraenterprises.entity.Tradeagreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TradeagreementController {

    @Autowired
    private TradeagreementDao dao;

    @RequestMapping(value = "/tradeagreement/list", method = RequestMethod.GET, produces = "application/json")
    public List<Tradeagreement> tradeagreements() { return dao.list(); }



}
