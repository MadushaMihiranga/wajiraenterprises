package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.*;
import com.wajiraenterprises.entity.*;
import com.wajiraenterprises.util.ModuleList;
import com.wajiraenterprises.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

@RestController
public class DeliveryconfirmationController {

    @Autowired
    private DeliveryconfirmationDao dao;

    @Autowired
    private DeliveryrequestDao deliveryrequestDao;

    @Autowired
    private VehicleDao vehicleDao;

    @Autowired
    private DeliveryDao deliveryDao;

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private ActivitylogDao activitylogDao;

    @Autowired
    private ActivitytypeDao activitytypeDao;

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/deliveryconfirmation", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Deliveryconfirmation> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size){

        if(AuthProvider.isUser(username,password)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    @RequestMapping(value = "/hasDeliveryConirmation", method = RequestMethod.GET,params = "number",produces = "application/json")
    public Boolean hasDeliveryConirmation(@RequestParam("number") String number) {

        Deliveryconfirmation deliveryconfirmation = dao.findByNumber(number);
        if (deliveryconfirmation != null){
            return true;
        }else {
            return false;
        }

    }

    @RequestMapping(value = "/getDeliveryConirmation", method = RequestMethod.GET,params = "number",produces = "application/json")
    public Deliveryconfirmation getDeliveryconfimation(@RequestParam("number") String number) {
        Deliveryconfirmation deliveryconfirmation;
        deliveryconfirmation = dao.findByNumber(number);
        System.out.println(deliveryconfirmation.getDeliveryrequestId());
        return deliveryconfirmation;
    }


    /**Add New Delivery Agent**/
    @Transactional
    @RequestMapping(value = "/deliveryconfirmation",method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Deliveryconfirmation deliveryconfirmation){

        if(AuthProvider.isAuthorized(username,password, ModuleList.DELIVERYCONFIRMATION,AuthProvider.INSERT)) {

            Deliveryrequest deliveryrequest = deliveryrequestDao.findbyNumber(deliveryconfirmation.getDeliveryrequestId().getNumber());

            if (deliveryrequest.getDeliveryrequeststatusId().getId() ==2){
                System.out.println("Vehicle for this request is already added");
                return "Vehicle for this request is already added";
            }else {
                try {

                    dao.save(deliveryconfirmation);


                    /**Change request status pending to confirmed after confirm request**/
                    Deliveryrequeststatus deliveryrequeststatus =  new Deliveryrequeststatus();
                    deliveryrequeststatus.setId(2);
                    deliveryrequest.setDeliveryrequeststatusId(deliveryrequeststatus);
                    deliveryrequestDao.save(deliveryrequest);

                    /**if ownership == by company change vehicle status to not available**/
                    if (deliveryconfirmation.getVehownershipId().getId()==1){
                        Vehicle vehicle =  vehicleDao.findByNumber(deliveryconfirmation.getVehicleId().getNumber());
                        Vehiclestatus vehiclestatus = new Vehiclestatus();
                        vehiclestatus.setId(2);
                        vehicle.setStatusId(vehiclestatus);
                        vehicleDao.save(vehicle);
                    }
                    /**add new Delivery**/
                    String deliveryynumber;
                    String checknull = deliveryDao.numbermax();
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
                        Scanner number1 = new Scanner(deliveryDao.numbermax()).useDelimiter("[^0-9]+");
                        Scanner number2 = new Scanner(deliveryDao.numbermax()).useDelimiter("[^0-9]+");
                        Scanner number3 = new Scanner(deliveryDao.numbermax()).useDelimiter("[^0-9]+");
                        boolean year = Integer.toString(number1.nextInt()).substring(0,2).equals(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2));
                        boolean month = Integer.toString(number2.nextInt()).substring(2,4).equals(getMonth);
                        boolean day = Integer.toString(number3.nextInt()).substring(4,6).equals(getDate);
                        if (year && month && day){
                            Scanner number = new Scanner(deliveryDao.numbermax()).useDelimiter("[^0-9]+");
                            num = Integer.toString(number.nextInt()+1);
                        }else {
                            num = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2)+getMonth+getDate+"01";
                        }
                        deliveryynumber =  "DLV-"+num;
                    }else {
                        num = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2)+getMonth+getDate+"01";
                        deliveryynumber =  "DLV-"+num;
                    }
                    //return "DLV-"+num;
                    Delivery delivery = new Delivery();
                    Deliverystatus status = new Deliverystatus();
                    status.setId(1);
                    delivery.setDeliverystatusId(status);
                    delivery.setNumber(deliveryynumber);
                    delivery.setDeliveryconfirmationId(deliveryconfirmation);
                    deliveryDao.save(delivery);
                    activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.DELIVERYCONFIRMATION.toString()),activitytypeDao.findByName("ADD"), " for Request No : "+deliveryconfirmation.getDeliveryrequestId().getNumber()));
                    notificationDao.save(Utilities.CreateNotification("Delivery Request "+deliveryconfirmation.getDeliveryrequestId().getNumber()+" Confirmed",4,deliveryconfirmation.getDeliveryrequestId().getRequestedby()));

                    if (deliveryconfirmation.getVehownershipId().getId() == 1){
                        notificationDao.save(Utilities.CreateNotification("New Delivery "+delivery.getNumber(),2,deliveryconfirmation.getDriver()));
                        if (deliveryconfirmation.getAssistant() != null){
                            notificationDao.save(Utilities.CreateNotification("New Delivery "+delivery.getNumber(),2,deliveryconfirmation.getAssistant()));
                        }
                    }

                    //System.out.println(deliveryconfirmation.getDeliveryrequestId().getNumber());

                    return "0";
                }
                catch (Exception e){
                    e.printStackTrace();
                    return "Error-Saving : " + e.getMessage();
                }
            }
        }
        return "Error-Saving : You have no Permission";
    }


    @RequestMapping(value = "/deliveryconfirmation", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Deliveryconfirmation deliveryconfirmation) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.DELIVERYCONFIRMATION,AuthProvider.UPDATE)) {

            try {
               Deliveryconfirmation oldDate =  dao.findByNumber(deliveryconfirmation.getDeliveryrequestId().getNumber());
               Vehicle oldVehicle =  vehicleDao.findByNumber(oldDate.getVehicleId().getNumber());
               Vehiclestatus vehiclestatus = new Vehiclestatus(1);
               oldVehicle.setStatusId(vehiclestatus);
               vehicleDao.save(oldVehicle);

                Vehicle newvehicle =  vehicleDao.findByNumber(deliveryconfirmation.getVehicleId().getNumber());
                Vehiclestatus newvehiclestatus = new Vehiclestatus(2);
                newvehicle.setStatusId(newvehiclestatus);
                vehicleDao.save(newvehicle);



                dao.save(deliveryconfirmation);
                activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.DELIVERYCONFIRMATION.toString()),activitytypeDao.findByName("UPDATE"), " for Request No : "+deliveryconfirmation.getDeliveryrequestId().getNumber()));
                System.out.println("delrequpd");
                return "0";
            } catch (Exception e) {
                return "Error-Saving : " + e.getMessage();
            }
        }else {
            return "Error-Saving : You have no Permission";
        }
    }


}
