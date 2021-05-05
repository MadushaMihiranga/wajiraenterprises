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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class CreditController {

    @Autowired
    private CreditDao creditDao;

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


    @RequestMapping(value = "/credit/bycompany",params ="number",method = RequestMethod.GET,produces = "application/json")
    public Credit placeofloading(@RequestParam("number") String number) { return creditDao.findByCompanyNumber(number); }


    @RequestMapping(value = "/credit", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Credit> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size){

        if(AuthProvider.isAuthorized(username,password, ModuleList.CREDIT,AuthProvider.SELECT)) {
            return creditDao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    /**Update**/

    @RequestMapping(value = "/credit", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Credit credit) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.CREDIT,AuthProvider.UPDATE)) {

            try {


                creditDao.save(credit);
                Company company = credit.getCompanyId();
                System.out.println(company.getName());
                Customerstatus customerstatus = new Customerstatus();

                if (credit.getMaxvalue().compareTo(credit.getValue()) <= 0 ){  //This method returns -1 if the BigDecimal is less than val, 1 if the BigDecimal is greater than val and 0 if the BigDecimal is equal to val
                    System.out.println("<=0");
                    customerstatus.setId(2);
                    company.setCustomerstatusId(customerstatus);
                }else {
                    customerstatus.setId(1);
                    company.setCustomerstatusId(customerstatus);
                }
                System.out.println("credit bs");
                companyDao.save(company);
                activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.CREDIT.toString()),activitytypeDao.findByName("UPDATE")," limit of the "+credit.getCompanyId().getName()+" )"));

                return "0";
            }
            catch(Exception e) {
                return "Error-Updating : "+e.getMessage();
            }


        }
        return "Error-Updating : You have no Permission";
    }

    @RequestMapping(value = "/credit", params = {"page", "size","customerid"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Credit> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("customerid") Integer customerid ) {
        //&number=&modsid=&customerid=&clearingclerkid=
        // System.out.println("customer-"+customerid);
        // System.out.println("SEARCH DO");

        if(AuthProvider.isAuthorized(username,password, ModuleList.CREDIT,AuthProvider.SELECT)) {

            List<Credit> credits = creditDao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Credit> creditStream = credits.stream();

            if (customerid != null) {creditStream = creditStream.filter(c -> c.getCompanyId().equals(companyDao.getOne(customerid)));}
            
            List<Credit> creditStream2 = creditStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < creditStream2.size() ? start + size : creditStream2.size();
            Page<Credit> despage = new PageImpl<>(creditStream2.subList(start, end), PageRequest.of(page, size), creditStream2.size());
            //System.out.println("searchresult : "+despage );

            return despage;
        }

        return null;
    }


}
