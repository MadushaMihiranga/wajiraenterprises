package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.CheckpointsDao;
import com.wajiraenterprises.entity.Checkpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CheckpointsController {

    @Autowired
    private CheckpointsDao dao;

    @RequestMapping(value = "/checkpoints/list", method = RequestMethod.GET, produces = "application/json")
    public List<Checkpoints> checkpoints() {
        return dao.list();
    }




}
