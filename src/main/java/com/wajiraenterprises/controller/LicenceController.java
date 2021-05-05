package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.LicenceDao;
import com.wajiraenterprises.entity.Licence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LicenceController {

    @Autowired
    private LicenceDao licenceDao;

    @RequestMapping(value = "/licence/list", method = RequestMethod.GET, produces = "application/json")
    public List<Licence> licence() { return licenceDao.list(); }

}
