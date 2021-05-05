package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.ModelDao;
import com.wajiraenterprises.entity.Model;
import com.wajiraenterprises.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ModelController {

    @Autowired
    private ModelDao dao;


    @RequestMapping(value = "/models/listbybrand", method = RequestMethod.GET,params = "brandId",produces = "application/json")
    public List<Model> models(@RequestParam("brandId") Integer brandId) { return dao.listByBrand(brandId); }

    @RequestMapping(value = "/models", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Model model) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.VEHICLE,AuthProvider.INSERT)) {
            Model mname = dao.findByName(model.getName());
            if (mname != null){
                System.out.println("Error-Validation : Model Exists");
                return "Error-Validation : Model Exists";
            }

            else{
                try {
                    dao.save(model);
                    System.out.println(model.getName());
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
            }

        }
        return "Error-Saving : You have no Permission";
    }


}
