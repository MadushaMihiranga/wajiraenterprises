package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.InventryDao;
import com.wajiraenterprises.dao.InvetryitemstatusDao;
import com.wajiraenterprises.entity.Inventry;
import com.wajiraenterprises.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class InventryController {

    @Autowired
    private InventryDao inventryDao;

    @Autowired
    private InvetryitemstatusDao invetryitemstatusDao;


    /***get Inventory items by shipment*/
    @RequestMapping(value = "/inventoryitem/listByShipmentNumber", method = RequestMethod.GET,params = "number",produces = "application/json")
    public List<Inventry> inventries(@RequestParam("number") String number) { return inventryDao.listByShipmentNumber(number); }


    @RequestMapping(value = "/inventry", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Inventry> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size){
        //System.out.println("Shipment Findall");
        if(AuthProvider.isAuthorized(username,password, ModuleList.INVENTORY,AuthProvider.SELECT)) {
            return inventryDao.findAll(PageRequest.of(page, size));
        }
        return null;
    }
    
    public String inventrynumber() {
        String checknull = inventryDao.numbermax();
        String num;
        Date date = new Date();
        String  getMonth;
        if (Integer.toString(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue()).length()==2){
            getMonth = Integer.toString(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue());
        }else {
            getMonth = "0"+date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue();
        }
        String getDate;
        if (Integer.toString(Calendar.getInstance().get(Calendar.DATE)).length()==2){
            getDate = Integer.toString(Calendar.getInstance().get(Calendar.DATE));
        }else {
            getDate = "0"+Calendar.getInstance().get(Calendar.DATE);
        }
        if (checknull != null){
            Scanner number1 = new Scanner(inventryDao.numbermax()).useDelimiter("[^0-9]+");
            Scanner number2 = new Scanner(inventryDao.numbermax()).useDelimiter("[^0-9]+");
            Scanner number3 = new Scanner(inventryDao.numbermax()).useDelimiter("[^0-9]+");
            boolean year = Integer.toString(number1.nextInt()).substring(0,2).equals(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2));
            boolean month = Integer.toString(number2.nextInt()).substring(2,4).equals(getMonth);
            boolean day = Integer.toString(number3.nextInt()).substring(4,6).equals(getDate);
            if (year && month && day){
                Scanner number = new Scanner(inventryDao.numbermax()).useDelimiter("[^0-9]+");
                num = Integer.toString(number.nextInt()+1);
            }else {
                num = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2)+getMonth+getDate+"01";
            }
            return "INV-"+num;
        }else {
            num = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2)+getMonth+getDate+"01";
            return "INV-"+num;
        }
    }


    @RequestMapping(value = "/inventry", params = {"page", "size","inventrynumber","shipmentnumber","itemname","statusid"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Inventry> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("inventrynumber") String inventrynumber, @RequestParam("shipmentnumber") String shipmentnumber, @RequestParam("itemname") String itemname, @RequestParam("statusid") Integer statusid) {

        //System.out.println("inventrynumber-"+inventrynumber+" / shipmentnumber-"+shipmentnumber+" / itemname-"+itemname+" statusid "+ statusid);

        if(AuthProvider.isAuthorized(username,password, ModuleList.INVENTORY,AuthProvider.SELECT)) {

            List<Inventry> inventries = inventryDao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Inventry> inventriesStream = inventries.stream();

            inventriesStream = inventriesStream.filter(c -> c.getNumber().contains(inventrynumber));
            inventriesStream = inventriesStream.filter(c -> c.getGrnhasitemId().getItemId().getShipmentId().getNumber().contains(shipmentnumber));
            inventriesStream = inventriesStream.filter(c -> c.getGrnhasitemId().getItemId().getDescription().startsWith(itemname));

            if (statusid != null) {inventriesStream = inventriesStream.filter(c -> c.getInvetryitemstatusId().equals(invetryitemstatusDao.getOne(statusid)));}



            List<Inventry> inventriesStream2 = inventriesStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < inventriesStream2.size() ? start + size : inventriesStream2.size();
            Page<Inventry> despage = new PageImpl<>(inventriesStream2.subList(start, end), PageRequest.of(page, size), inventriesStream2.size());
            //System.out.println("searchresult : "+despage );

            return despage;
        }

        return null;
    }


}
