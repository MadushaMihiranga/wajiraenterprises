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

import java.time.LocalDate;

@RestController
public class CustomeracceptenceController {
    @Autowired
    private CustomeracceptenceDao customeracceptenceDao;

    @Autowired
    private ClearingprocessDao clearingprocessDao;

    @Autowired
    private DeliveryDao deliveryDao;

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

    @RequestMapping(value = "/customeracceptenc", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Customeracceptence> customeracceptences(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size){

        if(AuthProvider.isUser(username,password)) {
            return customeracceptenceDao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    @Transactional
    @RequestMapping(value = "/customeracceptenc", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Customeracceptence customeracceptence) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.CUSTOMERACCEPTENCE,AuthProvider.INSERT)) {
            Clearingprocess clearingprocess = clearingprocessDao.findByNumber(customeracceptence.getShipmentId().getNumber());
            if (clearingprocess.getClearingprocessstatusId().getId()==9){
                return "This Shipment already finish";
            }else if (clearingprocess.getClearingprocessstatusId().getId()==10){
                return "This Shipment is Terminated";
            }else if (clearingprocess.getClearingprocessstatusId().getId()<=6){
                return "Claring Process of this shipment is not finish yet";
            }else {
                try {
                    if (customeracceptence.getAcceptencestatusId().getId()==1){

                        clearingprocess.setEndate(LocalDate.now());
                        Clearingprocessstatus clearingprocessstatus = new Clearingprocessstatus();
                        clearingprocessstatus.setId(9);
                        clearingprocess.setClearingprocessstatusId(clearingprocessstatus);
                        clearingprocessDao.save(clearingprocess);

                        if (customeracceptence.getShipmentId().getDeliverymethodId().getId() != 2){
                            Delivery delivery = customeracceptence.getDeliveryId();
                            Deliverystatus deliverystatus = new Deliverystatus();
                            deliverystatus.setId(2);
                            delivery.setDeliverystatusId(deliverystatus);
                            deliveryDao.save(delivery);

                            if (delivery.getDeliveryconfirmationId().getVehownershipId().getId()==1) {
                                Vehicle vehicle = customeracceptence.getDeliveryId().getDeliveryconfirmationId().getVehicleId();
                                Vehiclestatus vehiclestatus = new Vehiclestatus();
                                vehiclestatus.setId(1);
                                vehicle.setStatusId(vehiclestatus);
                                vehicleDao.save(vehicle);
                            }
                        }
                    }
                    customeracceptenceDao.save(customeracceptence);
                    activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.CUSTOMERACCEPTENCE.toString()),activitytypeDao.findByName("ADD")," for Shipment No : "+customeracceptence.getShipmentId().getNumber()));

                    System.out.println("cusacc");
                    return "0";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error-Saving : " + e.getMessage();
                }
            }
        }
        return "Error-Saving : You have no Permission";
    }



    @RequestMapping(value = "/customeracceptenc", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Customeracceptence customeracceptence) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.CUSTOMERACCEPTENCE,AuthProvider.INSERT)) {
            Clearingprocess clearingprocess = clearingprocessDao.findByNumber(customeracceptence.getShipmentId().getNumber());
            if (clearingprocess.getClearingprocessstatusId().getId()==9){
                return "This Shipment already finish";
            }else if (clearingprocess.getClearingprocessstatusId().getId()==10){
                return "This Shipment is Terminated";
            }else if (clearingprocess.getClearingprocessstatusId().getId()<=6){
                return "Claring Process of this shipment is not finish yet";
            }else {
                try {
                    customeracceptenceDao.save(customeracceptence);
                    activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.CUSTOMERACCEPTENCE.toString()),activitytypeDao.findByName("UPDATE")," for Shipment No : "+customeracceptence.getShipmentId().getNumber()));
                    System.out.println("cusacc");
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
            }
        }
        return "Error-Saving : You have no Permission";
    }



}
