package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.LocationDao;
import com.wajiraenterprises.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocationController {

    @Autowired
    private LocationDao dao;

    @RequestMapping(value = "/location/list",method = RequestMethod.GET, produces = "application/json")
    public List<Location> location(){return dao.list();}

}
