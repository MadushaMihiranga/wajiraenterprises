package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.*;
import com.wajiraenterprises.entity.*;
import com.wajiraenterprises.util.ModuleList;
import com.wajiraenterprises.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class GdnController {

    @Autowired
    private GdnDao gdnDao;

    @Autowired
    InventryDao inventryDao;

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private ActivitylogDao activitylogDao;

    @Autowired
    private ActivitytypeDao activitytypeDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/gdn", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Gdn> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size){
        if(AuthProvider.isAuthorized(username,password, ModuleList.GDN,AuthProvider.SELECT)) {
            System.out.println("GDN");
            return gdnDao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    @RequestMapping(value = "/hasgdn", method = RequestMethod.GET,params = "number",produces = "application/json")
    public Integer hasgdn(@RequestParam("number") String number) {
        Gdn gdn = gdnDao.findByNumber(number);
        if (gdn != null){
            return 1;
        }else {
            return 0;
        }

    }


    @RequestMapping(value = "/getgdn", method = RequestMethod.GET,params = "number",produces = "application/json")
    public Gdn getgdn(@RequestParam("number") String number) {
        Gdn gdn = gdnDao.findByNumber(number);
        return gdn;
    }






    @RequestMapping(value = "/gdn", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Gdn gdn) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.GDN,AuthProvider.INSERT)) {
            Gdn gdnno = gdnDao.findByNumber(gdn.getNumber());

            if ( gdnno!= null){
                System.out.println("GDN Added");
                return "GDN Added";
            }
            else{
                try {
                    for (Gdnhasinventoryitem gdnhasinventoryitem: gdn.getGdnhasinventoryitemList()) {
                        gdnhasinventoryitem.setGdnId(gdn);
                    }
                    gdnDao.save(gdn); /**Save GRN and GRN Item List Before Save Inventory*/
                    for (Gdnhasinventoryitem gdnhasinventoryitem: gdn.getGdnhasinventoryitemList()) {
                        Inventry inventry = inventryDao.getOne(gdnhasinventoryitem.getInventryId().getId());
                        inventry.setStoredqty(inventry.getStoredqty()-gdnhasinventoryitem.getQty());
                        Invetryitemstatus invetryitemstatus = new Invetryitemstatus();
                        invetryitemstatus.setId(2);
                        inventry.setInvetryitemstatusId(invetryitemstatus);
                        inventry.setDiscpatcheddate(LocalDate.now());
                        inventryDao.save(inventry);
                    }
                    activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.GDN.toString()),activitytypeDao.findByName("ADD"),gdn.getNumber()));

                    /**create Inventory for GRN Items**/
                    System.out.println("gdn");
                    return "0";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error-Saving : " + e.getMessage();
                }
            }
        }
        return "Error-Saving : You have no Permission";
    }


    @Transactional
    @RequestMapping(value = "/gdn", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Gdn gdn ) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.GDN,AuthProvider.DELETE)) {
            try {
                Gdn gdn1 = gdnDao.getOne(gdn.getId());

                /**Delete Inventry**/
                for (Gdnhasinventoryitem gdnhasinventoryitem: gdn1.getGdnhasinventoryitemList()) {
                   Inventry inventry =  gdnhasinventoryitem.getInventryId();
                   inventry.setDiscpatcheddate(null);
                   inventry.setStoredqty(inventry.getStoredqty()+gdnhasinventoryitem.getQty());
                   Invetryitemstatus invetryitemstatus = new Invetryitemstatus();
                   invetryitemstatus.setId(1);
                   inventry.setInvetryitemstatusId(invetryitemstatus);
                   inventryDao.save(inventry);
                }

                /**Delete GRN**/
                gdn1.getGdnhasinventoryitemList().remove(gdnDao);
                gdnDao.save(gdn1);
                gdnDao.delete(gdn);
                activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.GDN.toString()),activitytypeDao.findByName("DELETE"),gdn.getNumber()));

                return "0";

            } catch (Exception e) {
                return "Error-Deleting : " + e.getMessage();
            }
        }

        else
            return "Error-Deleting : You have no Permission";

    }


    /**Get Next Number**/
    @RequestMapping(value = "/gdn/nextnumber", method = RequestMethod.GET, produces = "application/json")
    public String grnnumber() {
        String checknull = gdnDao.numbermax();
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
            Scanner number1 = new Scanner(gdnDao.numbermax()).useDelimiter("[^0-9]+");
            Scanner number2 = new Scanner(gdnDao.numbermax()).useDelimiter("[^0-9]+");
            Scanner number3 = new Scanner(gdnDao.numbermax()).useDelimiter("[^0-9]+");
            boolean year = Integer.toString(number1.nextInt()).substring(0,2).equals(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2));
            boolean month = Integer.toString(number2.nextInt()).substring(2,4).equals(getMonth);
            boolean day = Integer.toString(number3.nextInt()).substring(4,6).equals(getDate);
            if (year && month && day){
                Scanner number = new Scanner(gdnDao.numbermax()).useDelimiter("[^0-9]+");
                num = Integer.toString(number.nextInt()+1);
            }else {
                num = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2)+getMonth+getDate+"01";
            }
            return "GDN-"+num;
        }else {
            num = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2)+getMonth+getDate+"01";
            return "GDN-"+num;
        }
    }


    @RequestMapping(value = "/gdn", params = {"page", "size","gdn","shipment",}, method = RequestMethod.GET, produces = "application/json")
    public Page<Gdn> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("gdn") String gdn, @RequestParam("shipment") String shipment) {

        //System.out.println("gdn-"+gdn+" / shipment-"+shipment+" / inventory "+ inventory);

        if(AuthProvider.isAuthorized(username,password, ModuleList.GRN,AuthProvider.SELECT)) {

            List<Gdn> grns = gdnDao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Gdn> grnStream = grns.stream();

            grnStream = grnStream.filter(c -> c.getNumber().contains(gdn));
            grnStream = grnStream.filter(c -> c.getShipmentId().getNumber().contains(shipment));



            List<Gdn>  grnStream2= grnStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < grnStream2.size() ? start + size : grnStream2.size();
            Page<Gdn> despage = new PageImpl<>(grnStream2.subList(start, end), PageRequest.of(page, size), grnStream2.size());
            //System.out.println("searchresult : "+despage );

            return despage;
        }

        return null;
    }


}
