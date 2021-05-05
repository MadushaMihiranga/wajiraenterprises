package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.ModuleDao;
import com.wajiraenterprises.entity.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ModuleController {

    @Autowired
    private ModuleDao dao;

    @RequestMapping(value = "/modules/list", method = RequestMethod.GET, produces = "application/json")
    public List<Module> gender() {
        return dao.list();
    }

    @RequestMapping(value = "/modules/list/unassignedtothisrole", params = {"roleid"}, method = RequestMethod.GET, produces = "application/json")
    public List<Module> modulesnotassignedtotherole(Integer roleid) {
        return dao.listUnassignedToThisRole(roleid);

    }


}
