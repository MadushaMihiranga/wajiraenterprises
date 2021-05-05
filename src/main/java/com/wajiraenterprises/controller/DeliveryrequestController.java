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

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class DeliveryrequestController {

    @Autowired
    private DeliveryrequestDao dao;

    @Autowired
    private ClearingprocessDao clearingprocessDao;

    @Autowired
    private DeliveryrequeststatusDao deliveryrequeststatusDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private ActivitylogDao activitylogDao;

    @Autowired
    private ActivitytypeDao activitytypeDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    NotificationDao notificationDao;


    @RequestMapping(value = "/deliveryrequest", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Deliveryrequest> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size){

        if(AuthProvider.isAuthorized(username,password, ModuleList.DELIVERYREQUEST,AuthProvider.SELECT)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    @RequestMapping(value = "/findshipment/deliveryrequest", method = RequestMethod.GET,params = "number",produces = "application/json")
    public Integer getshipment(@RequestParam("number") String number) {
        Shipment shipment;
        Clearingprocess clearingprocess ;
       try {
           shipment = clearingprocessDao.findByNumber(number).getShipmentId();
           clearingprocess = clearingprocessDao.findByNumber(shipment.getNumber());
           if (clearingprocess.getClearingprocessstatusId().getId()<6){
               return 5;
           }else if (clearingprocess.getClearingprocessstatusId().getId()==10){
               return 4;
           }else if (clearingprocess.getClearingprocessstatusId().getId()==9){
               return 3;
           }else {
               return 1;
           }
       }catch (Exception e){
           return 0;
       }
    }

    @RequestMapping(value = "/deliveryrequest/nextnumber", method = RequestMethod.GET, produces = "application/json")
    public String requestnumber() {
        String checknull = dao.numbermax();
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
            Scanner number1 = new Scanner(dao.numbermax()).useDelimiter("[^0-9]+");
            Scanner number2 = new Scanner(dao.numbermax()).useDelimiter("[^0-9]+");
            Scanner number3 = new Scanner(dao.numbermax()).useDelimiter("[^0-9]+");
            boolean year = Integer.toString(number1.nextInt()).substring(0,2).equals(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2));
            boolean month = Integer.toString(number2.nextInt()).substring(2,4).equals(getMonth);
            boolean day = Integer.toString(number3.nextInt()).substring(4,6).equals(getDate);
            if (year && month && day){
                Scanner number = new Scanner(dao.numbermax()).useDelimiter("[^0-9]+");
                num = Integer.toString(number.nextInt()+1);
            }else {
                num = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2)+getMonth+getDate+"01";
            }
            return "DLR-"+num;
        }else {
            num = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2)+getMonth+getDate+"01";
            return "DLR-"+num;
        }
    }

    @Transactional
    @RequestMapping(value = "/deliveryrequest", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Deliveryrequest deliveryrequest) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.DELIVERYREQUEST,AuthProvider.UPDATE)) {
            try {

                Employee employee = employeeDao.getByDesignation(3);

                dao.save(deliveryrequest);
                activitylogDao.save(Objects.requireNonNull(Utilities.CreateActivityLog(userDao.findByUsername(username), moduleDao.findByName(ModuleList.DELIVERYREQUEST.toString()), activitytypeDao.findByName("ADD"), deliveryrequest.getNumber() + " for Shipment No : " + deliveryrequest.getShipmentId().getNumber())));
                notificationDao.save(Objects.requireNonNull(Utilities.CreateNotification("New Delivery Request For Shipment " + deliveryrequest.getShipmentId().getNumber() + " to " + deliveryrequest.getDeliverytypeId().getName(), 3, employee)));
                return "0";
            }
            catch(Exception e) {
                e.printStackTrace();
                return "Error-Updating : "+e.getMessage();
            }
        }
        return "Error-Saving : You have no Permission";

/*        if(AuthProvider.isAuthorized(username,password, ModuleList.DELIVERYREQUEST,AuthProvider.INSERT)) {
            Deliveryrequest hasdeliveryrequest = dao.findByShipment(deliveryrequest.getShipmentId().getNumber());
            System.out.println(hasdeliveryrequest);
            if (hasdeliveryrequest != null){
                if (hasdeliveryrequest.getDeliverytypeId().getId().equals(deliveryrequest.getDeliverytypeId().getId())){
                    System.out.println("Delivery request for this shipment is already added");
                    return "Delivery request for this shipment is already added";
                }
                else{
                    try {
                        System.out.println(deliveryrequest.getShipmentId().getNumber());
                        dao.save(deliveryrequest);

                        System.out.println("deliveryrequest add");
                        return "0";
                    } catch (Exception e) {
                        e.printStackTrace();
                        return "Error-Saving : " + e.getMessage();
                    }
                }
            }
        }
        return "Error-Saving : You have no Permission";*/
    }


    @RequestMapping(value = "/deliveryrequest", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Deliveryrequest deliveryrequest){

        if(AuthProvider.isAuthorized(username,password,ModuleList.DELIVERYREQUEST,AuthProvider.UPDATE)) {
            try {
                dao.save(deliveryrequest);
                activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.DELIVERYREQUEST.toString()),activitytypeDao.findByName("UPDATE"), deliveryrequest.getNumber()));

                return "0";
            }
            catch(Exception e) {
                return "Error-Updating : "+e.getMessage();
            }
        }
        return "Error-Saving : You have no Permission";
    }


    /**Search Functions***/
    @RequestMapping(value = "/deliveryrequest", params = {"page", "size","requestnumber","shipmentno","statusid","clearingclerkid"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Deliveryrequest> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("requestnumber") String requestnumber, @RequestParam("shipmentno") String shipmentno, @RequestParam("statusid") Integer statusid, @RequestParam("clearingclerkid") Integer clearingclerkid) {
        //&number=&modsid=&customerid=&clearingclerkid=
        //System.out.println("Number-"+number+" / Mod-"+modsid+" / Customer-"+customerid+" - ClearingClerk-"+clearingclerkid);

        if(AuthProvider.isAuthorized(username,password, ModuleList.DELIVERYREQUEST,AuthProvider.SELECT)) {

            //System.out.println(shipmentno+" - "+ shipmentno+"  -  "+statusid+" +- "+clearingclerkid);


            List<Deliveryrequest> deliveryrequests = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Deliveryrequest> deliveryrequestStream = deliveryrequests.stream();

            deliveryrequestStream = deliveryrequestStream.filter(s -> s.getShipmentId().getNumber().contains(shipmentno));

            deliveryrequestStream = deliveryrequestStream.filter(s -> s.getNumber().contains(requestnumber));

            if (statusid != null) {deliveryrequestStream = deliveryrequestStream.filter(s -> s.getDeliveryrequeststatusId().equals(deliveryrequeststatusDao.getOne(statusid)));}

            if (clearingclerkid != null) {deliveryrequestStream = deliveryrequestStream.filter(s -> s.getRequestedby().equals(employeeDao.getOne(clearingclerkid)));}

            List<Deliveryrequest> deliveryrequestStream2 = deliveryrequestStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < deliveryrequestStream2.size() ? start + size : deliveryrequestStream2.size();
            Page<Deliveryrequest> despage = new PageImpl<>(deliveryrequestStream2.subList(start, end), PageRequest.of(page, size), deliveryrequestStream2.size());
            //System.out.println("searchresult : "+despage );

            return despage;
        }

        return null;
    }


}
