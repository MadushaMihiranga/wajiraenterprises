package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.CurrencyDao;
import com.wajiraenterprises.entity.Currency;
import com.wajiraenterprises.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CurrencyController {

    @Autowired
    private CurrencyDao dao;

  /**Currency List**/
    @RequestMapping(value = "/currency/list", method = RequestMethod.GET, produces = "application/json")
    public List<Currency> currencies() { return dao.list(); }

    /**Add Currency Unit**/
    @RequestMapping(value = "/currency", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Currency currency) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.SHIPMENT,AuthProvider.INSERT)) {
            Currency cname = dao.findByName(currency.getName());
            Currency ccode = dao.findByCode(currency.getCode());
            Currency cnumber = dao.findByNumber(currency.getNumber());
            if (cname != null){
                System.out.println("Error-Validation : Currency Name Exists");
                return "Error-Validation : Name Exists";
            }else if (ccode != null){
                System.out.println("Error-Validation : Currency Code Exists");
                return "Error-Validation : Code Exists";
            }else if (cnumber != null){
                System.out.println("Error-Validation : Currency Number Exists");
                return "Error-Validation : Number Exists";
            }
            else{
                try {
                    dao.save(currency);
                    System.out.println(currency.getName());
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
            }
        }
        return "Error-Saving : You have no Permission";
    }

}
