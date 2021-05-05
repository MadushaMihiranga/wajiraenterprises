package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.ClearingprocessDao;
import com.wajiraenterprises.dao.DeliveryDao;
import com.wajiraenterprises.dao.DeliverystatusDao;
import com.wajiraenterprises.entity.Clearingprocess;
import com.wajiraenterprises.entity.Delivery;
import com.wajiraenterprises.entity.Shipment;
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
public class DeliveryController {

    @Autowired
    private DeliveryDao deliveryDao;

    @Autowired
    private ClearingprocessDao clearingprocessDao;

    @Autowired
    private DeliverystatusDao deliverystatusDao;


    @RequestMapping(value = "/delivery", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Delivery> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size){
        if(AuthProvider.isAuthorized(username,password, ModuleList.DELIVERY,AuthProvider.SELECT)) {
            return deliveryDao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    @RequestMapping(value = "/finddelivery", method = RequestMethod.GET,params = "number",produces = "application/json")
    public Integer hasdelivery(@RequestParam("number") String number) {

        try {
            Delivery delivery = deliveryDao.findByNumber(number);
            if (delivery != null){
                return 1;
            }else {
                return 0;
            }

        }catch (Exception e){
            return 3;
        }
    }

    @RequestMapping(value = "/getdelivery", method = RequestMethod.GET,params = "number",produces = "application/json")
    public Delivery getdelivery(@RequestParam("number") String number) {
        Delivery delivery = deliveryDao.findByNumber(number);
        return delivery;
    }

    @RequestMapping(value = "/delivery", params = {"page", "size","shpnumber","delnumber","statusid"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Delivery> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("shpnumber") String shpnumber, @RequestParam("delnumber") String delnumber, @RequestParam("statusid") Integer statusid) {
        //&number=&modsid=&customerid=&clearingclerkid=
        //System.out.println("Number-"+number+" / customer-"+customerid+" / refno-"+refno+" assessmentno "+ assessmentno);
        // System.out.println("SEARCH DO");

        if(AuthProvider.isAuthorized(username,password, ModuleList.DELIVERY,AuthProvider.SELECT)) {

            List<Delivery> deliveries = deliveryDao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Delivery> deliveryStream = deliveries.stream();

            deliveryStream = deliveryStream.filter(c -> c.getDeliveryconfirmationId().getDeliveryrequestId().getShipmentId().getNumber().contains(shpnumber));

            if (statusid != null) {deliveryStream = deliveryStream.filter(c -> c.getDeliverystatusId().equals(deliverystatusDao.getOne(statusid)));}

            deliveryStream = deliveryStream.filter(c -> c.getNumber().contains(delnumber));



            List<Delivery> deliveryStream2 = deliveryStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < deliveryStream2.size() ? start + size : deliveryStream2.size();
            Page<Delivery> despage = new PageImpl<>(deliveryStream2.subList(start, end), PageRequest.of(page, size), deliveryStream2.size());
            //System.out.println("searchresult : "+despage );

            return despage;
        }

        return null;
    }



/*    @RequestMapping(value = "/delivery/nextnumber", method = RequestMethod.GET, produces = "application/json")
    public String deliverynumber() {
        Scanner number1 = new Scanner(deliveryDao.numbermax()).useDelimiter("[^0-9]+");
        Scanner number2 = new Scanner(deliveryDao.numbermax()).useDelimiter("[^0-9]+");
        Scanner number3 = new Scanner(deliveryDao.numbermax()).useDelimiter("[^0-9]+");
        String num;
        Date date = new Date();
        String  getMonth;
        if (Integer.toString(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue()).length()==2){
            getMonth = Integer.toString(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue());
        }else {
            getMonth = "0"+date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue();
        }
        boolean year = Integer.toString(number1.nextInt()).substring(0,2).equals(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2));
        boolean month = Integer.toString(number2.nextInt()).substring(2,4).equals(getMonth);
        boolean day = Integer.toString(number3.nextInt()).substring(4,6).equals(Integer.toString(Calendar.getInstance().get(Calendar.DATE)));
        if (year && month && day){
            Scanner number = new Scanner(deliveryDao.numbermax()).useDelimiter("[^0-9]+");
            num = Integer.toString(number.nextInt()+1);
        }else {
            num = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2)+getMonth+Calendar.getInstance().get(Calendar.DATE)+"01";
        }
        return "DLV-"+num;
    }*/




}
