package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.*;
import com.wajiraenterprises.entity.Cheque;
import com.wajiraenterprises.entity.Chequestatus;
import com.wajiraenterprises.entity.Credit;
import com.wajiraenterprises.entity.Payment;
import com.wajiraenterprises.util.ModuleList;
import com.wajiraenterprises.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class PaymentController {

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private CreditDao creditDao;

    @Autowired
    private PaymenttypeDao paymenttypeDao;

    @Autowired
    private EmployeeDao employeeDao;

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

    @RequestMapping(value = "/payment", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Payment> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size){

        if(AuthProvider.isAuthorized(username,password, ModuleList.PAYMENT,AuthProvider.SELECT)) {
            return paymentDao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    /**Get Next Number**/
    @RequestMapping(value = "/payment/nextnumber", method = RequestMethod.GET, produces = "application/json")
    public String grnnumber() {
        return Utilities.generateNumber(paymentDao.numbermax(),"PAY-","",0);
    }
    /**Add**/
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Payment payment) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.PAYMENT,AuthProvider.INSERT)) {
            try {

                Credit credit = creditDao.findByCompanyNumber(payment.getCompanyId().getNumber());
                credit.setValue(credit.getValue().subtract(payment.getAmount()));
                System.out.println(credit.getValue());

                for (Cheque cheque: payment.getChequeList()) {
                    Chequestatus chequestatus = new Chequestatus();
                    chequestatus.setId(1);
                    cheque.setChequestatusId(chequestatus);
                    cheque.setPaymentId(payment);
                }
                paymentDao.save(payment);
                creditDao.save(credit);
                activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.PAYMENT.toString()),activitytypeDao.findByName("ADD"),payment.getNumber()+" ( received from : "+payment.getCompanyId().getName()+" )"));
                System.out.println("payment");
                return "0";
            } catch (Exception e) {
                return "Error-Saving : " + e.getMessage();
            }
        }
        return "Error-Saving : You have no Permission";
    }


    @RequestMapping(value = "/payment", params = {"page", "size","payment","typeid","customerid","employeeid"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Payment> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("payment") String payment, @RequestParam("typeid") Integer typeid, @RequestParam("customerid") Integer customerid, @RequestParam("employeeid") Integer employeeid ) {
        //&number=&modsid=&customerid=&clearingclerkid=
        // System.out.println("Number-"+number+" / customer-"+customerid+" / refno-"+refno+" assessmentno "+ assessmentno);
        // System.out.println("SEARCH DO");

        if(AuthProvider.isAuthorized(username,password, ModuleList.PAYMENT,AuthProvider.SELECT)) {

            List<Payment> payments = paymentDao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Payment> paymentStream = payments.stream();

            paymentStream = paymentStream.filter(c -> c.getNumber().contains(payment));

            if (typeid != null) {paymentStream = paymentStream.filter(c -> c.getPaymenttypeId().equals(paymenttypeDao.getOne(typeid)));}

            if (employeeid != null) {paymentStream = paymentStream.filter(c -> c.getEmployeeId().equals(employeeDao.getOne(employeeid)));}

            if (customerid != null) {paymentStream = paymentStream.filter(c -> c.getCompanyId().equals(companyDao.getOne(customerid)));}

            paymentStream = paymentStream.filter(c -> c.getNumber().contains(payment));



            List<Payment> paymentStream2 = paymentStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < paymentStream2.size() ? start + size : paymentStream2.size();
            Page<Payment> despage = new PageImpl<>(paymentStream2.subList(start, end), PageRequest.of(page, size), paymentStream2.size());
            //System.out.println("searchresult : "+despage );

            return despage;
        }

        return null;
    }



}
