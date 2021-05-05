package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.PlaceofloadingordischargeDao;
import com.wajiraenterprises.entity.Placeofloadingordischarge;
import com.wajiraenterprises.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlaceofloadingordischargeController {

    @Autowired
    private PlaceofloadingordischargeDao dao;



    @RequestMapping(value = "/placeofloadingordischarges/list", method = RequestMethod.GET, produces = "application/json")
    public List<Placeofloadingordischarge> placeofloadingordischarges() { return dao.list(); }


    @RequestMapping(value = "/placeofloading/listbymodnadcountry",params ={"modsId","countryId"},method = RequestMethod.GET,produces = "application/json")
    public List<Placeofloadingordischarge> placeofloading(@RequestParam("modsId") Integer modsId,@RequestParam("countryId") Integer countryId) { return dao.listByModIdAndCountryId(modsId,countryId); }

    /**Add Place of Loading**/
    @RequestMapping(value = "/placeofloadingordischarge", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Placeofloadingordischarge placeofloadingordischarge) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.SHIPMENT,AuthProvider.INSERT)) {
            Placeofloadingordischarge pname = dao.findByName(placeofloadingordischarge.getName());
            if (pname != null){
                System.out.println("Error-Validation : Place Exists");
                return "Error-Validation : Place Exists";
            }

            else{
                try {
                    dao.save(placeofloadingordischarge);
                    System.out.println(placeofloadingordischarge.getName());
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
            }
        }
        return "Error-Saving : You have no Permission";
    }


}
