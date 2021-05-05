package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.CountryDao;
import com.wajiraenterprises.entity.Country;
import com.wajiraenterprises.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CountryController {

    @Autowired
    private CountryDao dao;

    @RequestMapping(value = "/country/list",method = RequestMethod.GET, produces = "application/json")
    public List<Country> country(){return dao.list();}

    @RequestMapping(value = "/countries", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Country country) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.SHIPMENT,AuthProvider.INSERT)) {

            Country cname = dao.findByName(country.getName());
            Country cnumericcode = dao.findByNumericCode(country.getNumericcode());
            Country ctwodigitcode = dao.findByTwoDigitCode(country.getTwodigitcode());

            if (cname != null){
                System.out.println("Error-Validation : Country Name Exists");
                return "Error-Validation : Country Exists";
            }
            else if (cnumericcode != null){
                return "Error-Validation : Numeric Code Exists";
            }
            else if (ctwodigitcode != null){
                return "Error-Validation : Two Digit Code Exists";
            }
            else{
                try {
                    dao.save(country);
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
