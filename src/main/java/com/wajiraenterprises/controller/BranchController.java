package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.BranchDao;
import com.wajiraenterprises.entity.Branch;
import com.wajiraenterprises.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BranchController {

    @Autowired
    private BranchDao dao;

    @RequestMapping(value = "/branch/listbybank", method = RequestMethod.GET,params = "bankId",produces = "application/json")
    public List<Branch> branches(@RequestParam("bankId") Integer bankId) { return dao.listByBank(bankId); }

    /**Add New Branch***/
    @RequestMapping(value = "/branch", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Branch branch) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.VEHICLE,AuthProvider.INSERT)) {
            Branch bname = null; //dao.findByName(branch.getName());
            Branch bcode = null; //dao.findByCode(branch.getCode());
            if (bname != null){
                System.out.println("Error-Validation : Branch Name Exists");
                return "Error-Validation : Branch Name Exists";
            }else  if (bcode != null){
                System.out.println("Error-Validation : Branch Code Exists");
                return "Error-Validation : Branch Code Exists";
            }
            else{
                try {
                    dao.save(branch);
                    System.out.println(branch.getName());
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
            }

        }
        return "Error-Saving : You have no Permission";
    }



}
