package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.VehiclerentelcompanyDao;
import com.wajiraenterprises.entity.Vehiclerentelcompany;
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
public class VehiclerentelcompanyController {

    @Autowired
    private VehiclerentelcompanyDao dao;
    @RequestMapping(value = "/vehiclerentelcompany", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Vehiclerentelcompany> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size){

        if(AuthProvider.isUser(username,password)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    @RequestMapping(value = "/vehiclerentelcompany/list", method = RequestMethod.GET, produces = "application/json")
    public List<Vehiclerentelcompany> vehiclerentelcompanylist() { return dao.list(); }


    /**Add New Delivery Agent**/
    @RequestMapping(value = "/vehiclerentelcompany",method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Vehiclerentelcompany vehiclerentelcompany){

        if(AuthProvider.isAuthorized(username,password, ModuleList.DELIVERYCONFIRMATION,AuthProvider.INSERT)) {

            Vehiclerentelcompany vname  = dao.findByName(vehiclerentelcompany.getName());

            if (vname != null){
                return "Error-Validation : Delivery Agent Exists";
            } else {
                try {
                    dao.save(vehiclerentelcompany);
                    return "0";
                }
                catch (Exception e){
                    return "Error-Saving : " + e.getMessage();
                }
            }
        }
        return "Error-Saving : You have no Permission";
    }

    /**Update Delivery Agent**/
    @RequestMapping(value = "/vehiclerentelcompany", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Vehiclerentelcompany vehiclerentelcompany){

        if(AuthProvider.isAuthorized(username,password,ModuleList.DELIVERYCONFIRMATION,AuthProvider.UPDATE)) {
            Vehiclerentelcompany vid = dao.findByName(vehiclerentelcompany.getName());
            if(vid==null || vid.getId()==vid.getId()) {
                try {
                    dao.save(vehiclerentelcompany);
                    return "0";
                }
                catch(Exception e) {
                    return "Error-Updating : "+e.getMessage();
                }
            }
            else {  return "Error-Updating : Delivery Agent Exists"; }
        }
        return "Error-Saving : You have no Permission";
    }

    /**Delete Delivery Agent**/
    @RequestMapping(value = "/vehiclerentelcompany", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Vehiclerentelcompany vehiclerentelcompany){
        if(AuthProvider.isAuthorized(username,password,ModuleList.DELIVERYCONFIRMATION,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(vehiclerentelcompany.getId()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";
    }


    /**Search Vehicle Rentel Company company**/
    @RequestMapping(value = "/vehiclerentelcompany", params = {"page", "size","name"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Vehiclerentelcompany> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("name") String name) {
        //System.out.println("Delivery Agent Seach");
        //System.out.println(statusid+" - "+typeid+" - "+driverid+" - "+" - "+assitantid+" - "+brandid+" - "+modelid);

        if(AuthProvider.isAuthorized(username,password, ModuleList.DELIVERYCONFIRMATION,AuthProvider.SELECT)) {

            List<Vehiclerentelcompany> vehiclerentelcompanies = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Vehiclerentelcompany> vehiclerentelcompanyStream = vehiclerentelcompanies.stream();
            vehiclerentelcompanyStream = vehiclerentelcompanyStream.filter(d -> d.getName().startsWith(name));
            List<Vehiclerentelcompany> vehiclerentelcompanyStream2 = vehiclerentelcompanyStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < vehiclerentelcompanyStream2.size() ? start + size : vehiclerentelcompanyStream2.size();
            Page<Vehiclerentelcompany> despage = new PageImpl<>(vehiclerentelcompanyStream2.subList(start, end), PageRequest.of(page, size), vehiclerentelcompanyStream2.size());
            //System.out.println("seardh123");

            return despage;
        }

        return null;

    }


}
