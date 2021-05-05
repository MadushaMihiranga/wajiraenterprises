package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.BrandDao;
import com.wajiraenterprises.entity.Brand;
import com.wajiraenterprises.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BrandController {

    @Autowired
    private BrandDao dao;

    @RequestMapping(value = "/brands/list", method = RequestMethod.GET, produces = "application/json")
    public List<Brand> brand() { return dao.list(); }


    @RequestMapping(value = "/brands", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Brand brand) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.VEHICLE,AuthProvider.INSERT)) {
            Brand bname = dao.findByName(brand.getName());
             if (bname != null){
                System.out.println("Error-Validation : Brand Exists");
                return "Error-Validation : Brand Exists";
            }
             else{
                try {
                    dao.save(brand);
                    //System.out.println(brand.getName());
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
            }
        }
        return "Error-Saving : You have no Permission";

    }



}
