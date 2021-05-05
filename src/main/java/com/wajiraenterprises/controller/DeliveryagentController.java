package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.DeliveryagentDao;
import com.wajiraenterprises.entity.Deliveryagent;
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
public class DeliveryagentController {

    @Autowired
    private DeliveryagentDao dao;

    @RequestMapping(value = "/deliveryagents/list", method = RequestMethod.GET, produces = "application/json")
    public List<Deliveryagent> deliveryagents() { return dao.list(); }

    /**Find All***/
    @RequestMapping(value = "/deliveryagents", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Deliveryagent> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size){

        if(AuthProvider.isUser(username,password)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    /**Add New Delivery Agent**/
    @RequestMapping(value = "/deliveryagent",method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Deliveryagent deliveryagent){

        if(AuthProvider.isAuthorized(username,password, ModuleList.SHIPMENT,AuthProvider.INSERT)) {

            Deliveryagent dname  = dao.findByName(deliveryagent.getName());

            if (dname != null){
                return "Error-Validation : Delivery Agent Exists";
            } else {
                try {
                    dao.save(deliveryagent);
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
    @RequestMapping(value = "/deliveryagent", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Deliveryagent deliveryagent){

        if(AuthProvider.isAuthorized(username,password,ModuleList.SHIPMENT,AuthProvider.UPDATE)) {
            Deliveryagent dlc = dao.findByName(deliveryagent.getName());
            if(dlc==null || dlc.getId()==deliveryagent.getId()) {
                try {
                    dao.save(deliveryagent);
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
    @RequestMapping(value = "/deliveryagent", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Deliveryagent deliveryagent){
        if(AuthProvider.isAuthorized(username,password,ModuleList.SHIPMENT,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(deliveryagent.getId()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";
    }

    /**Search Delivery Agent company**/
    @RequestMapping(value = "/deliveryagents", params = {"page", "size","name"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Deliveryagent> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("name") String name) {
        //System.out.println("Delivery Agent Seach");
        //System.out.println(statusid+" - "+typeid+" - "+driverid+" - "+" - "+assitantid+" - "+brandid+" - "+modelid);

        if(AuthProvider.isAuthorized(username,password, ModuleList.SHIPMENT,AuthProvider.SELECT)) {

            List<Deliveryagent> deliveryagents = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Deliveryagent> deliveryagentStream = deliveryagents.stream();
            deliveryagentStream = deliveryagentStream.filter(d -> d.getName().startsWith(name));
            List<Deliveryagent> deliveryagentStream2 = deliveryagentStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < deliveryagentStream2.size() ? start + size : deliveryagentStream2.size();
            Page<Deliveryagent> despage = new PageImpl<>(deliveryagentStream2.subList(start, end), PageRequest.of(page, size), deliveryagentStream2.size());
            //System.out.println("seardh123");

            return despage;
        }

        return null;

    }

}
