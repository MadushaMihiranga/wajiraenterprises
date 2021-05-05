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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class PaytaxesController {

    @Autowired
    private PaytaxesDao paytaxesDao;

    @Autowired
    private ClearingprocessDao clearingprocessDao;

    @Autowired
    private PaymenttypeDao paymenttypeDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private ActivitylogDao activitylogDao;

    @Autowired
    private ActivitytypeDao activitytypeDao;

    @Autowired
    private UserDao userDao;


    @RequestMapping(value = "/paytaxes", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Paytaxes> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.CLEARINGPROCESS,AuthProvider.SELECT)) {
            return paytaxesDao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    @RequestMapping(value = "/hasTaxPayments", method = RequestMethod.GET,params = "number",produces = "application/json")
    public Boolean hasTaxPayments(@RequestParam("number") String number) {

        Paytaxes paytaxes = paytaxesDao.findByShipmentNo(number);
        if (paytaxes != null){
            return true;
        }else {
            return false;
        }

    }

    @RequestMapping(value = "/getTaxPayments", method = RequestMethod.GET,params = "number",produces = "application/json")
    public Paytaxes getTaxPayments(@RequestParam("number") String number) {

        Paytaxes paytaxes = paytaxesDao.findByShipmentNo(number);
        return paytaxes;
    }


    @RequestMapping(value = "/paytaxes", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Paytaxes paytaxes) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.CLEARINGPROCESS,AuthProvider.INSERT)) {
           // Paytaxes clearinprocessno = paytaxesDao.findByClearingProcessno(paytaxes.getClearingprocessId());

         /*   if ( clearinprocessno!= null){
                System.out.println("Taxes for this shipment already paid");
                return "Taxes for this shipment already paid";
            }*/

           // else{
                try {
                    System.out.println("test");

                    Clearingprocess clearingprocess;
                    Clearingprocessstatus clearingprocessstatus = new Clearingprocessstatus();
                    clearingprocessstatus.setId(5);
                    clearingprocess = clearingprocessDao.findByNumber(paytaxes.getClearingprocessId().getShipmentId().getNumber());
                    clearingprocess.setClearingprocessstatusId(clearingprocessstatus);
                    clearingprocessDao.save(clearingprocess);

                    //System.out.println( paytaxes.getCusdecId());

                    paytaxesDao.save(paytaxes);
                    activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.TAXPAYMENT.toString()),activitytypeDao.findByName("ADD"),"for Shipment No : "+paytaxes.getClearingprocessId().getShipmentId().getNumber()));

                    //System.out.println("paytaxes");
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
            }

        //}
        return "Error-Saving : You have no Permission";
    }


    @RequestMapping(value = "/paytaxes", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Paytaxes paytaxes) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.CLEARINGPROCESS,AuthProvider.UPDATE)) {
            try {
                paytaxesDao.save(paytaxes);
                activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.TAXPAYMENT.toString()),activitytypeDao.findByName("UPDATE"),"for Shipment No : "+paytaxes.getClearingprocessId().getShipmentId().getNumber()));
                return "0";
            } catch (Exception e) {
                return "Error-Saving : " + e.getMessage();
            }
        }else {
            return "Error-Saving : You have no Permission";
        }
    }



    @RequestMapping(value = "/paytaxes", params = {"page", "size","number","customerid","paymenttypeid","cusdecrefno"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Paytaxes> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("number") String number, @RequestParam("customerid") Integer customerid, @RequestParam("paymenttypeid") Integer paymenttypeid , @RequestParam("cusdecrefno") String cusdecrefno) {
        //&number=&modsid=&customerid=&clearingclerkid=
        // System.out.println("Number-"+number+" / customer-"+customerid+" / deliveryagent-"+deliveragentid);
        // System.out.println("SEARCH DO");

        if(AuthProvider.isAuthorized(username,password, ModuleList.CLEARINGPROCESS,AuthProvider.SELECT)) {

            List<Paytaxes> collectdos = paytaxesDao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Paytaxes> paytaxesStream = collectdos.stream();

            paytaxesStream = paytaxesStream.filter(s -> s.getClearingprocessId().getShipmentId().getNumber().contains(number));

            paytaxesStream = paytaxesStream.filter(s -> s.getCusdecId().getCustomrefno().startsWith(cusdecrefno));

            if (customerid != null) {paytaxesStream = paytaxesStream.filter(s -> s.getClearingprocessId().getShipmentId().getCompanyId().equals(companyDao.getOne(customerid)));}

            if (paymenttypeid != null) {paytaxesStream = paytaxesStream.filter(c -> c.getPaymenttypeId().equals(paymenttypeDao.getOne(paymenttypeid)));}

            List<Paytaxes> paytaxesStream2 = paytaxesStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < paytaxesStream2.size() ? start + size : paytaxesStream2.size();
            Page<Paytaxes> despage = new PageImpl<>(paytaxesStream2.subList(start, end), PageRequest.of(page, size), paytaxesStream2.size());
            //System.out.println("searchresult : "+despage );

            return despage;
        }

        return null;
    }


}
