package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.ShippingcompanyDao;
import com.wajiraenterprises.entity.Shippingcompany;
import com.wajiraenterprises.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class ShippingcompanyController {

    @Autowired
    private ShippingcompanyDao dao;

    @RequestMapping(value = "/shippingcompany/list", method = RequestMethod.GET, produces = "application/json")
    public List<Shippingcompany> shippingcompanie() { return dao.list(); }

    /**Find All Shipping Company***/
    @RequestMapping(value = "/shippingcompany", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Shippingcompany> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.SHIPMENT,AuthProvider.SELECT)) {
            System.out.println("recived");
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    /**Add New Shipping Company**/
    @RequestMapping(value = "/shippingcompany",method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Shippingcompany shippingcompany){

        if(AuthProvider.isAuthorized(username,password, ModuleList.SHIPMENT,AuthProvider.INSERT)) {

            Shippingcompany sname  = dao.findByName(shippingcompany.getName());
            Shippingcompany saddress  = dao.findByAddress(shippingcompany.getAddress());
            Shippingcompany scontactno  = dao.findByContactNo(shippingcompany.getContactno());
            Shippingcompany semail = dao.findByEmail(shippingcompany.getEmail());

            if (sname != null){
                return "Error-Validation : Shipping Company Exists";
            }
            else if (saddress != null){
                return "Error-Validation : Address Exists";
            }
            else if (scontactno != null){
                return "Error-Validation : Contact NoExists";
            }
            else if (semail != null){
                return "Error-Validation : E-mail Exists";
            }
            else {
                try {
                    dao.save(shippingcompany);
                    return "0";
                }
                catch (Exception e){
                    return "Error-Saving : " + e.getMessage();
                }
            }
        }
        return "Error-Saving : You have no Permission";
    }


    /**Update Shipping Company**/
    @RequestMapping(value = "/shippingcompany", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Shippingcompany shippingcompany){

        if(AuthProvider.isAuthorized(username,password,ModuleList.SHIPMENT,AuthProvider.UPDATE)) {

            if(AuthProvider.isAuthorized(username,password,ModuleList.EMPLOYEE,AuthProvider.UPDATE)) {
                Shippingcompany shp = dao.findByName(shippingcompany.getName());
                if(shp==null || shp.getId()==shippingcompany.getId()) {
                    try {
                        dao.save(shippingcompany);
                        return "0";
                    }
                    catch(Exception e) {
                        return "Error-Updating : "+e.getMessage();
                    }
                }
                else {  return "Error-Updating : NIC Exists"; }
            }
        }
        return "Error-Saving : You have no Permission";
    }


    /**Delete Shipping Company**/
    @RequestMapping(value = "/shippingcompany", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Shippingcompany shippingcompany){
        if(AuthProvider.isAuthorized(username,password,ModuleList.SHIPMENT,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(shippingcompany.getId()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }
    /**Search Shipping company**/
    @RequestMapping(value = "/shippingcompany", params = {"page", "size","name"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Shippingcompany> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("name") String name) {

        //System.out.println(statusid+" - "+typeid+" - "+driverid+" - "+" - "+assitantid+" - "+brandid+" - "+modelid);

        if(AuthProvider.isAuthorized(username,password, ModuleList.SHIPMENT,AuthProvider.SELECT)) {

            List<Shippingcompany> shippingcompanies = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Shippingcompany> shippingcompanyStream = shippingcompanies.stream();
            shippingcompanyStream = shippingcompanyStream.filter(s -> s.getName().startsWith(name));
            List<Shippingcompany> shippingcompanyStream2 = shippingcompanyStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < shippingcompanyStream2.size() ? start + size : shippingcompanyStream2.size();
            Page<Shippingcompany> despage = new PageImpl<>(shippingcompanyStream2.subList(start, end), PageRequest.of(page, size), shippingcompanyStream2.size());
            //System.out.println("seardh123");

            return despage;
        }

        return null;

    }

}
