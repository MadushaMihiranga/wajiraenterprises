package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.VehicletypeDao;
import com.wajiraenterprises.entity.Vehicletype;
import com.wajiraenterprises.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VehicleTypeController {

    @Autowired
    private VehicletypeDao dao;

    @RequestMapping(value = "/vehicletype/list", method = RequestMethod.GET, produces = "application/json")
    public List<Vehicletype> vehicletype() { return dao.list(); }

    @RequestMapping(value = "/vehicletype", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Vehicletype vehicletype) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.VEHICLE,AuthProvider.INSERT)) {
            Vehicletype vehicletypename = dao.findByName(vehicletype.getName());

           if (vehicletypename != null){
                System.out.println("Error-Validation : Type Exists");
                return "Error-Validation : Type Exists";
            }

            else{
                try {
                    dao.save(vehicletype);
                    //System.out.println(vehicletype.getName());
                    return "0";
                } catch (Exception e) {
                    ///System.out.println("Brnad");
                    return "Error-Saving : " + e.getMessage();
                }
            }

        }
        return "Error-Saving : You have no Permission";

    }



}
