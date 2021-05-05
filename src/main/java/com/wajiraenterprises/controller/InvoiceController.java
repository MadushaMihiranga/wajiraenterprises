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

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class InvoiceController {

    @Autowired
    private InvoiceDao invoiceDao;

    @Autowired
    private ShipmentDao shipmentDao;

    @Autowired
    private ServicesquotationDao servicesquotationDao;

    @Autowired
    private DeliveryDao deliveryDao;

    @Autowired
    private InventryDao inventryDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private CreditDao creditDao;

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private ActivitylogDao activitylogDao;

    @Autowired
    private ActivitytypeDao activitytypeDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/invoice", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Invoice> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size){
        if(AuthProvider.isAuthorized(username,password, ModuleList.INVOICE,AuthProvider.SELECT)) {
            return invoiceDao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    @RequestMapping(value = "/invoice/nextnumber", method = RequestMethod.GET, produces = "application/json")
    public String grnnumber() {
        String checknull = invoiceDao.numbermax();
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
            Scanner number1 = new Scanner(invoiceDao.numbermax()).useDelimiter("[^0-9]+");
            Scanner number2 = new Scanner(invoiceDao.numbermax()).useDelimiter("[^0-9]+");
            Scanner number3 = new Scanner(invoiceDao.numbermax()).useDelimiter("[^0-9]+");
            boolean year = Integer.toString(number1.nextInt()).substring(0,2).equals(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2));
            boolean month = Integer.toString(number2.nextInt()).substring(2,4).equals(getMonth);
            boolean day = Integer.toString(number3.nextInt()).substring(4,6).equals(getDate);
            if (year && month && day){
                Scanner number = new Scanner(invoiceDao.numbermax()).useDelimiter("[^0-9]+");
                num = Integer.toString(number.nextInt()+1);
            }else {
                num = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2)+getMonth+getDate+"01";
            }
            return "WE/INV-"+num;
        }else {
            num = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2)+getMonth+getDate+"01";
            return "WE/INV-"+num;
        }
    }


    /**Price calculation **************************************************************************/

    @RequestMapping(value = "/getprice", method = RequestMethod.GET,params = {"shipmentno", "service"},produces = "application/json")
    public Invoicehasservice invoicehasservice(@RequestParam("shipmentno") String shipmentno,@RequestParam("service") int service) {
        Invoicehasservice invoicehasservice = new Invoicehasservice();

        Shipment shipment = shipmentDao.findByNumber(shipmentno);
        Servicesquotation servicesquotation = servicesquotationDao.findQuation(shipment.getModsId().getId(),shipment.getContainertypeId().getId(),service);
        Servicesquotation extraTrasportCharge = servicesquotationDao.findQuation(shipment.getModsId().getId(),shipment.getContainertypeId().getId(),10);

        BigDecimal price = new BigDecimal("0.00");
        Integer discount = 0;
        BigDecimal discountPrice = new BigDecimal("0.00");
        BigDecimal totalPrice = new BigDecimal("0.00");
        String description = "";

        List<Delivery> delivery = deliveryDao.findByShipmentNumber(shipment.getNumber());

        if (servicesquotation.getServicesId().getId()==9){ /**Calculate Transport Charges    >> **/ //TODO:01-05 CBM
            BigDecimal deliverycharge = new BigDecimal("0.00");
            Integer CargoVolume;
            Integer ExtraCargoVolume;
            if (shipment.getDeliverymethodId().getId()==1){ //1 = Delivery By Company | 2= Delivery By Customer
                for (int del = 0; del < delivery.size(); del++ ){
                    if (delivery.get(del).getDeliveryconfirmationId().getVehownershipId().getId()==1){    //Vehicle ownership = 1 Company | 2 = Outsource
                        if (delivery.get(del).getDeliveryconfirmationId().getDeliveryrequestId().getCargovolume()>5){
                            CargoVolume = delivery.get(del).getDeliveryconfirmationId().getDeliveryrequestId().getCargovolume();
                            ExtraCargoVolume = CargoVolume-5;
                            deliverycharge = servicesquotation.getValue().add(extraTrasportCharge.getValue().multiply(new BigDecimal(ExtraCargoVolume)));
                            description ="Cargo size " +CargoVolume+" Cubic Meters "+delivery.size()+" Trips";
                        }else {
                            deliverycharge = servicesquotation.getValue();
                        }
                    }else if (delivery.get(del).getDeliveryconfirmationId().getVehownershipId().getId()==2){
                        deliverycharge = BigDecimal.valueOf(0.00); // add value when create invoice by user
                    }
                    price = price.add(deliverycharge);
                }
            }
        }else if (servicesquotation.getServicesId().getId()==12){ /**Calculate WareHouse Charges**/
            List <Inventry> inventries= inventryDao.listByShipmentNumber(shipment.getNumber());
            long NoOfDays = ChronoUnit.DAYS.between(inventries.get(0).getStoredate(),inventries.get(0).getDiscpatcheddate()); //inventries.get(0).getStoredate()
            price = servicesquotation.getValue().multiply(new BigDecimal(delivery.get(0).getDeliveryconfirmationId().getDeliveryrequestId().getCargovolume())).multiply(new BigDecimal(NoOfDays));
            description = delivery.get(0).getDeliveryconfirmationId().getDeliveryrequestId().getCargovolume()+" Cubic Meters Stored "+NoOfDays+" Days";
        }else {
            price = servicesquotation.getValue();
        }

        //Calculate Discount Value
        discountPrice = price.multiply(new BigDecimal(discount)).divide(new BigDecimal(100));
        totalPrice = price.subtract(discountPrice);

        /*BigDecimal test = new BigDecimal(discount);
        System.out.println("discoun pre: "+discount);
        System.out.println("test:"+test);
        System.out.println("price:"+price);
        //System.out.println("discountPricepre:"+discountPricepre);
        //System.out.println("discountPricepre2:"+discountPricepre2);
        System.out.println("discountprice:"+discountPrice);
        System.out.println("totalprice:"+totalPrice);*/

        invoicehasservice.setServicesquotationId(servicesquotation);
        invoicehasservice.setPrice(price);
        invoicehasservice.setDiscount(discount);
        invoicehasservice.setTotalprice(totalPrice);
        invoicehasservice.setDescription(description);

        return invoicehasservice;
    }
    /**Price calculation **************************************************************************/

    @RequestMapping(value = "/invoice", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Invoice invoice) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.INVOICE,AuthProvider.INSERT)) {
            Invoice invoicebyshipment = invoiceDao.findByShipmentNumber(invoice.getShipmentId().getNumber());

            if ( invoicebyshipment!= null){
                System.out.println("Customs Declaration for this shipment is already added");
                return "Customs Declaration for this shipment is already added";
            }

            else{
                try {

                    Credit credit = creditDao.findByCompanyNumber(invoice.getShipmentId().getCompanyId().getNumber());
                    credit.setValue(credit.getValue().add(invoice.getGrandtotal()));

                    for (Invoicehasservice invoicehasservice: invoice.getInvoicehasserviceList()) {
                        invoicehasservice.setInvoiceId(invoice);
                    }
                    //System.out.println("GT ="+invoice.getGrandtotal());
                    //invoice.setPaidtotal(invoice.getGrandtotal());
                    //System.out.println("PT = "+invoice.getPaidtotal());
                    invoiceDao.save(invoice);
                    System.out.println("invoice");
                    creditDao.save(credit);
                    activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.INVOICE.toString()),activitytypeDao.findByName("ADD"),invoice.getNumber()+" for Shipment No : "+invoice.getShipmentId().getNumber()));

                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
            }

        }
        return "Error-Saving : You have no Permission";
    }

    @RequestMapping(value = "/invoice", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Invoice invoice) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.INVOICE,AuthProvider.UPDATE)) {
            Invoice invoicebyshipment = invoiceDao.findByShipmentNumber(invoice.getShipmentId().getNumber());


                try {

                    for (Invoicehasservice invoicehasservice: invoice.getInvoicehasserviceList()) {
                        invoicehasservice.setInvoiceId(invoice);
                    }
                    invoiceDao.save(invoice);
                    activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.INVOICE.toString()),activitytypeDao.findByName("UPDATE"),invoice.getNumber()+" for Shipment No : "+invoice.getShipmentId().getNumber()));
                    System.out.println("cusdec");
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }


        }
        return "Error-Saving : You have no Permission";
    }


    @Transactional
    @RequestMapping(value = "/invoice", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Invoice invoice ) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.INVOICE,AuthProvider.DELETE)) {
            try {
                Credit credit = creditDao.findByCompanyNumber(invoice.getShipmentId().getCompanyId().getNumber());
                credit.setValue(credit.getValue().subtract(invoice.getGrandtotal()));
                Invoice invoice1 = invoiceDao.getOne(invoice.getId());
                invoice1.getInvoicehasserviceList().remove(invoiceDao);
                invoiceDao.save(invoice1);
                invoiceDao.delete(invoice);
                creditDao.save(credit);
                activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.INVOICE.toString()),activitytypeDao.findByName("DELETE"),invoice.getNumber()+" for Shipment No : "+invoice.getShipmentId().getNumber()));

                return "0";
            } catch (Exception e) {
                return "Error-Deleting : " + e.getMessage();
            }
        }
        else
            return "Error-Deleting : You have no Permission";

    }

    @RequestMapping(value = "/invoice", params = {"page", "size","invoice","shipment","customerid"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Invoice> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("invoice") String invoice, @RequestParam("shipment") String shipment, @RequestParam("customerid") Integer customerid ) {
        //&number=&modsid=&customerid=&clearingclerkid=
       // System.out.println("Number-"+number+" / customer-"+customerid+" / refno-"+refno+" assessmentno "+ assessmentno);
        // System.out.println("SEARCH DO");

        if(AuthProvider.isAuthorized(username,password, ModuleList.INVOICE,AuthProvider.SELECT)) {

            List<Invoice> invoices = invoiceDao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Invoice> invoiceStream = invoices.stream();

            invoiceStream = invoiceStream.filter(c -> c.getShipmentId().getNumber().contains(shipment));

            if (customerid != null) {invoiceStream = invoiceStream.filter(c -> c.getShipmentId().getCompanyId().equals(companyDao.getOne(customerid)));}

            invoiceStream = invoiceStream.filter(c -> c.getNumber().contains(invoice));

            List<Invoice> invoiceStream2 = invoiceStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < invoiceStream2.size() ? start + size : invoiceStream2.size();
            Page<Invoice> despage = new PageImpl<>(invoiceStream2.subList(start, end), PageRequest.of(page, size), invoiceStream2.size());
            //System.out.println("searchresult : "+despage );

            return despage;
        }

        return null;
    }



}
