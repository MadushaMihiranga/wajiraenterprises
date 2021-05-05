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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class GrnController {

    @Autowired
    private GrnDao grnDao;

    @Autowired
    private ClearingprocessDao clearingprocessDao;

    @Autowired
    private InventryDao inventryDao;

    @Autowired
    private DeliveryDao deliveryDao;

    @Autowired
    private GrnhasitemDao grnhasitemDao;

    @Autowired
    private VehicleDao vehicleDao;

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private ActivitylogDao activitylogDao;

    @Autowired
    private ActivitytypeDao activitytypeDao;

    @Autowired
    private UserDao userDao;



    @RequestMapping(value = "/grn", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Grn> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size){
        if(AuthProvider.isAuthorized(username,password, ModuleList.GRN,AuthProvider.SELECT)) {
            return grnDao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    /**Get Next Number**/
    /**Get Next Number**/
    @RequestMapping(value = "/grn/nextnumber", method = RequestMethod.GET, produces = "application/json")
    public String grnnumber() {
        String checknull = grnDao.numbermax();
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
            Scanner number1 = new Scanner(grnDao.numbermax()).useDelimiter("[^0-9]+");
            Scanner number2 = new Scanner(grnDao.numbermax()).useDelimiter("[^0-9]+");
            Scanner number3 = new Scanner(grnDao.numbermax()).useDelimiter("[^0-9]+");
            boolean year = Integer.toString(number1.nextInt()).substring(0,2).equals(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2));
            boolean month = Integer.toString(number2.nextInt()).substring(2,4).equals(getMonth);
            boolean day = Integer.toString(number3.nextInt()).substring(4,6).equals(getDate);
            if (year && month && day){
                Scanner number = new Scanner(grnDao.numbermax()).useDelimiter("[^0-9]+");
                num = Integer.toString(number.nextInt()+1);
            }else {
                num = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2)+getMonth+getDate+"01";
            }
            return "GRN-"+num;
        }else {
            num = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2)+getMonth+getDate+"01";
            return "GRN-"+num;
        }
    }


    @RequestMapping(value = "/grn", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Grn grn) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.GRN,AuthProvider.INSERT)) {
            Grn grnno = grnDao.findByDeliveryNo(grn.getDeliveryId().getNumber());

            if ( grnno!= null){
                System.out.println("Goods Received Note for this delivery is already added");
                return "Goods Received Note for this delivery is already added";
            }
            else{
                try {

                    for (Grnhasitem grnhasitem: grn.getGrnhasitemList()) {
                        grnhasitem.setGrnId(grn);
                    }
                    grnDao.save(grn); /**Save GRN and GRN Item List Before Save Inventory*/
                    activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.GRN.toString()),activitytypeDao.findByName("ADD"),grn.getNumber()+" for Deliver No : "+grn.getDeliveryId().getNumber()));


                    /**create Inventory for GRN Items**/
                    for (Grnhasitem grnhasitem: grn.getGrnhasitemList()) {
                        Inventry inventry = new Inventry();
                        Invetryitemstatus invetryitemstatus = new Invetryitemstatus();
                        String inventorynumber;
                       /* InventryController inventryController = new InventryController();
                        System.out.println(inventryController.inventrynumber());*/
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
                            inventorynumber =  "INV-"+num;
                        }else {
                            num = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2)+getMonth+getDate+"01";
                            inventorynumber =  "INV-"+num;
                        }
                        inventry.setNumber(inventorynumber); /**check null**///
                        inventry.setGrnhasitemId(grnhasitem);
                        inventry.setStoredqty(grnhasitem.getRecivedqty());
                        inventry.setStoredate(LocalDate.now());
                        invetryitemstatus.setId(1);
                        inventry.setInvetryitemstatusId(invetryitemstatus);
                       // inventry.setShipmentId(grn.getShipmentId());
                        inventryDao.save(inventry);
                    }

                    Clearingprocess clearingprocess;
                    Clearingprocessstatus clearingprocessstatus = new Clearingprocessstatus();
                    if (grn.getShipmentId().getDeliverymethodId().getId()==1){
                        clearingprocessstatus.setId(8);
                    }else if (grn.getShipmentId().getDeliverymethodId().getId()==2){
                        clearingprocessstatus.setId(7);
                    }
                    clearingprocess = clearingprocessDao.findByNumber(grn.getShipmentId().getNumber());
                    clearingprocess.setClearingprocessstatusId(clearingprocessstatus);
                    clearingprocessDao.save(clearingprocess);

                    Delivery delivery = deliveryDao.findByNumber(grn.getDeliveryId().getNumber());
                    Deliverystatus deliverystatus = new Deliverystatus();
                    deliverystatus.setId(2);
                    delivery.setDeliverystatusId(deliverystatus);
                    deliveryDao.save(delivery);

                    if (delivery.getDeliveryconfirmationId().getVehownershipId().getId()==1){
                        Vehicle vehicle = delivery.getDeliveryconfirmationId().getVehicleId();
                        Vehiclestatus vehiclestatus = new Vehiclestatus();
                        vehiclestatus.setId(1);
                        vehicle.setStatusId(vehiclestatus);
                        vehicleDao.save(vehicle);
                    }
                    System.out.println("grn");
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
            }
        }
        return "Error-Saving : You have no Permission";
    }




    @RequestMapping(value = "/grn", params = {"page", "size","grn","shipment","delivery"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Grn> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("grn") String grn, @RequestParam("shipment") String shipment, @RequestParam("delivery") String delivery) {
        System.out.println("grn-"+grn+" / shipment-"+shipment+" / delivery "+ delivery);
        if(AuthProvider.isAuthorized(username,password, ModuleList.GRN,AuthProvider.SELECT)) {
            List<Grn> grns = grnDao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Grn> grnStream = grns.stream();
            grnStream = grnStream.filter(c -> c.getNumber().contains(grn));
            grnStream = grnStream.filter(c -> c.getShipmentId().getNumber().contains(shipment));
            grnStream = grnStream.filter(c -> c.getDeliveryId().getNumber().contains(delivery));
            List<Grn>  grnStream2= grnStream.collect(Collectors.toList());
            int start = page * size;
            int end = start + size < grnStream2.size() ? start + size : grnStream2.size();
            Page<Grn> despage = new PageImpl<>(grnStream2.subList(start, end), PageRequest.of(page, size), grnStream2.size());
            //System.out.println("searchresult : "+despage );
            return despage;
        }
        return null;
    }


    @Transactional
    @RequestMapping(value = "/grn", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Grn grn ) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.GRN,AuthProvider.DELETE)) {
            try {
                Grn grn1 = grnDao.getOne(grn.getId());

                /**Delete Inventry**/
                for (Grnhasitem grnhasitem: grn1.getGrnhasitemList()) {
                    Inventry inventry = inventryDao.findByItemId(grnhasitem.getItemId().getId());
                    System.out.println(inventry.getNumber());
                    inventryDao.delete(inventry);
                    System.out.println("2");

                }
                System.out.println("3");

                Delivery delivery = deliveryDao.findByNumber(grn.getDeliveryId().getNumber());
                Deliverystatus deliverystatus = new Deliverystatus();
                deliverystatus.setId(1);
                delivery.setDeliverystatusId(deliverystatus);
                deliveryDao.save(delivery);
                System.out.println("4");

                Clearingprocess clearingprocess;
                Clearingprocessstatus clearingprocessstatus = new Clearingprocessstatus();
                clearingprocess = clearingprocessDao.findByNumber(grn.getShipmentId().getNumber());
                clearingprocessstatus.setId(6);
                clearingprocess.setClearingprocessstatusId(clearingprocessstatus);
                clearingprocessDao.save(clearingprocess);
                System.out.println("5");

                /**Delete GRN**/
                grn1.getGrnhasitemList().remove(grnDao);
                System.out.println("6");
                grnDao.save(grn1); /** or - grn****/
                System.out.println("7");
                grnDao.delete(grn);
                activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.GRN.toString()),activitytypeDao.findByName("DELETE"),grn.getNumber()));

                System.out.println("8");
                return "0";

            } catch (Exception e) {
                return "Error-Deleting : " + e.getMessage();
            }
        }

        else
            return "Error-Deleting : You have no Permission";

    }







}
