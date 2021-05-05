package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.BankDao;
import com.wajiraenterprises.entity.Bank;
import com.wajiraenterprises.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BankController {

    @Autowired
    private BankDao dao;

    @RequestMapping(value = "/banks/list", method = RequestMethod.GET, produces = "application/json")
    public List<Bank> banks() { return dao.list(); }

    @RequestMapping(value = "/bank", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Bank bank) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.VEHICLE,AuthProvider.INSERT)) {
            Bank bname = dao.findByName(bank.getName());
            Bank bcode = dao.findByCode(bank.getCode());
            if (bname != null){
                System.out.println("Error-Validation : Bank Name Exists");
                return "Error-Validation : Bank Name Exists";
            }else if (bcode!= null){
                System.out.println("Error-Validation : Bank Code Exists");
                return "Error-Validation : Bank Code Exists";
            }

            else{
                try {
                    dao.save(bank);
                    System.out.println(bank.getName());
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
            }

        }
        return "Error-Saving : You have no Permission";

    }

}
