package com.wajiraenterprises.controller;




import com.wajiraenterprises.entity.ReportEnitity1;
import com.wajiraenterprises.util.ModuleList;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class ReportController {



    @RequestMapping(value = "/reports/systemaccessanalysis", method = RequestMethod.GET, produces = "application/json")
    public List<ReportEntitySystemAccessAnalysis> systemaccessanalysis(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
if (AuthProvider.isAuthorized(username,password, ModuleList.EMPLOYEE,AuthProvider.SELECT)) {
    LocalDateTime startdate = LocalDateTime.now().minusDays(35);
    LocalDateTime enddate = LocalDateTime.now().plusDays(1);
    return ReportProvider.getSystemAccessAnalysisReport(startdate, enddate);
}
else return  null;


    }

    @RequestMapping(value = "/reports/systemaccessanalysis", params = {"startdate", "enddate"}, method = RequestMethod.GET, produces = "application/json")
    public List<ReportEntitySystemAccessAnalysis> systemaccessanalysis2(@CookieValue(value="username") String username, @CookieValue(value="password") String password,@RequestParam("startdate") String startdate,@RequestParam("enddate") String enddate) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.EMPLOYEE,AuthProvider.SELECT)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime stdate=LocalDateTime.parse(startdate+" 00:00",formatter );
            LocalDateTime endate=LocalDateTime.parse(enddate +" 00:00",formatter);
            return ReportProvider.getSystemAccessAnalysisReport(stdate,endate);
        }
        else return  null;
    }

    @RequestMapping(value = "/reports/customershipment", method = RequestMethod.GET, produces = "application/json")
    public List<ReportEnitity1> customershipmentreport(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.SHIPMENT,AuthProvider.SELECT)) {
            return ReportProvider.getcustomershipmentreport();
        }
        else return  null;
    }

    @RequestMapping(value = "/reports/shipmentbymodes", method = RequestMethod.GET, produces = "application/json")
    public List<ReportEnitity1> shipmentbymodes(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.SHIPMENT,AuthProvider.SELECT)) {
            return ReportProvider.getShipmentModes();
        }
        else return  null;
    }

    @RequestMapping(value = "/reports/shipmentbystatus", method = RequestMethod.GET, produces = "application/json")
    public List<ReportEnitity1> shipmentByClearingProcessStatus(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.SHIPMENT,AuthProvider.SELECT)) {
            return ReportProvider.getShipmentByClearingProcessStatus();
        }
        else return  null;
    }

    @RequestMapping(value = "/reports/shipmentByModandCustomer", method = RequestMethod.GET, produces = "application/json")
    public List<ReportEnitity1> shipmentbymodesandCustomer(@CookieValue(value="username") String username, @CookieValue(value="password") String password,@RequestParam("companyId") Integer companyId) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.SHIPMENT,AuthProvider.SELECT)) {
            return ReportProvider.getShipmentModesadnCustomer(companyId);
        }
        else return  null;
    }

    @RequestMapping(value = "/reports/deliverybyvehicle", method = RequestMethod.GET, produces = "application/json")
    public List<ReportEnitity1> deliveryByShipment(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.DELIVERY,AuthProvider.SELECT)) {
            return ReportProvider.getDeliveryByVehicle();
        }
        else return  null;
    }

    @RequestMapping(value = "/reports/deliverybydriver", method = RequestMethod.GET, produces = "application/json")
    public List<ReportEnitity1> deliverybyvehicle(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.DELIVERY,AuthProvider.SELECT)) {
            return ReportProvider.getDeliveryByDriver();
        }
        else return  null;
    }

    @RequestMapping(value = "/reports/shipmetnbyclearingclerk", method = RequestMethod.GET, produces = "application/json")
    public List<ReportEnitity1> shipmetnbyclearingclerk(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.DELIVERY,AuthProvider.SELECT)) {
            return ReportProvider.getShipmentByClearingClerk();
        }
        else return  null;
    }

    @RequestMapping(value = "/reports/shipmenttimerange", method = RequestMethod.GET, produces = "application/json")
    public List<shipmenttimerange> shipmenttimerange(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.SHIPMENT,AuthProvider.SELECT)) {
            LocalDateTime startdate = LocalDateTime.now().minusDays(35);
            LocalDateTime enddate = LocalDateTime.now().plusDays(1);
            return ReportProvider.getshipmetndaterange(startdate, enddate);
        }
        else return  null;


    }

    @RequestMapping(value = "/reports/shipmenttimerange", params = {"startdate", "enddate"}, method = RequestMethod.GET, produces = "application/json")
    public List<shipmenttimerange> shipmenttimerange2(@CookieValue(value="username") String username, @CookieValue(value="password") String password,@RequestParam("startdate") String startdate,@RequestParam("enddate") String enddate) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.SHIPMENT,AuthProvider.SELECT)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime stdate=LocalDateTime.parse(startdate+" 00:00",formatter );
            LocalDateTime endate=LocalDateTime.parse(enddate +" 00:00",formatter);
            return ReportProvider.getshipmetndaterange(stdate,endate);
        }
        else return  null;
    }





}
