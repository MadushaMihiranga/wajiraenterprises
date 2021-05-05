package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.*;
import com.wajiraenterprises.entity.Clearingprocess;
import com.wajiraenterprises.entity.Clearingprocessstatus;
import com.wajiraenterprises.entity.Deliveryagent;
import com.wajiraenterprises.entity.Shipment;
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
public class ClearingprocessController {

    @Autowired
    private ClearingprocessDao clearingprocessDao;

    @Autowired
    private ClearingprocessstatusDao clearingprocessstatusDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private ShipmentDao shipmentDao;

    @RequestMapping(value = "/getClearingprocessobject", method = RequestMethod.GET,params = "shipmentno",produces = "application/json")
    public Clearingprocess clearingprocess(@RequestParam("shipmentno") String shipmentno) {
        Clearingprocess clearingprocess;
        clearingprocess = clearingprocessDao.findByNumber(shipmentno);
        return clearingprocess;
    }

    @RequestMapping(value = "/getshipment", method = RequestMethod.GET,params = "shipmentno",produces = "application/json")
    public Shipment shipment(@RequestParam("shipmentno") String shipmentno) {
        Shipment shipment;
        shipment = clearingprocessDao.findByNumber(shipmentno).getShipmentId();
        return shipment;
    }
    @RequestMapping(value = "/findshipment/clearingprocessid", method = RequestMethod.GET,params = "number",produces = "application/json")
    public Integer getshipment(@RequestParam("number") String number) {
        Shipment shipment;
        Clearingprocess clearingprocess ;
        try {
            shipment = clearingprocessDao.findByNumber(number).getShipmentId();
            clearingprocess = clearingprocessDao.findByNumber(shipment.getNumber());
            return clearingprocess.getClearingprocessstatusId().getId();

        }catch (Exception e){
            return 0;
        }
    }


    @RequestMapping(value = "/getdeliveryagent", method = RequestMethod.GET,params = "shipmentno",produces = "application/json")
    public Deliveryagent deliveryagent(@RequestParam("shipmentno") String shipmentno) {
        Clearingprocess clearingprocess;
        clearingprocess = clearingprocessDao.findByNumber(shipmentno);

        return clearingprocess.getShipmentId().getDeliveryagentId();
    }

    @RequestMapping(value = "/clearingprocess/bycompany", method = RequestMethod.GET,params = "comapnyId",produces = "application/json")
    public List<Clearingprocess> models(@RequestParam("comapnyId") Integer comapnyId) { return clearingprocessDao.listByCompanyId(comapnyId); }


    @RequestMapping(value = "/getclearingprocess", method = RequestMethod.POST)
    public String getclearingprocess(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Shipment shipment) {

       Clearingprocess clearingprocess ;
       clearingprocess = clearingprocessDao.findByNumber(shipment.getNumber());

       if (shipmentDao.findByNumber(shipment.getNumber()) != null){
           return clearingprocess.getClearingprocessstatusId().getId().toString();
       }else {
           return "0";
       }
    }

    @RequestMapping(value = "/clearingprocess", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Clearingprocess> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size){

        if(AuthProvider.isAuthorized(username,password, ModuleList.CLEARINGPROCESS,AuthProvider.SELECT)) {
            return clearingprocessDao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    /**Search Functions***/
    @RequestMapping(value = "/clearingprocess", params = {"page", "size","number","statusid","customerid","clearingclerkid"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Clearingprocess> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("number") String number, @RequestParam("statusid") Integer statusid, @RequestParam("customerid") Integer customerid, @RequestParam("clearingclerkid") Integer clearingclerkid) {
        //&number=&modsid=&customerid=&clearingclerkid=
        //System.out.println("Number-"+number+" / Mod-"+modsid+" / Customer-"+customerid+" - ClearingClerk-"+clearingclerkid);

        if(AuthProvider.isAuthorized(username,password, ModuleList.CLEARINGPROCESS,AuthProvider.SELECT)) {

            List<Clearingprocess> clearingprocesses = clearingprocessDao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Clearingprocess> clearingprocessStream = clearingprocesses.stream();

            clearingprocessStream = clearingprocessStream.filter(s -> s.getShipmentId().getNumber().contains(number));

            if (statusid != null) {clearingprocessStream = clearingprocessStream.filter(s -> s.getClearingprocessstatusId().equals(clearingprocessstatusDao.getOne(statusid)));}

            if (customerid != null) {clearingprocessStream = clearingprocessStream.filter(s -> s.getShipmentId().getCompanyId().equals(companyDao.getOne(customerid)));}

            if (clearingclerkid != null) {clearingprocessStream = clearingprocessStream.filter(s -> s.getShipmentId().getClearingclerk().equals(employeeDao.getOne(clearingclerkid)));}

            List<Clearingprocess> clearingprocessStream2 = clearingprocessStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < clearingprocessStream2.size() ? start + size : clearingprocessStream2.size();
            Page<Clearingprocess> despage = new PageImpl<>(clearingprocessStream2.subList(start, end), PageRequest.of(page, size), clearingprocessStream2.size());
            //System.out.println("searchresult : "+despage );

            return despage;
        }

        return null;
    }


}
