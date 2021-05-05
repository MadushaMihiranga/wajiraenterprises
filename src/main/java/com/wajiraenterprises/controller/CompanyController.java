package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.*;
import com.wajiraenterprises.entity.Company;
import com.wajiraenterprises.entity.Credit;
import com.wajiraenterprises.entity.Customerstatus;
import com.wajiraenterprises.util.ModuleList;
import com.wajiraenterprises.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class CompanyController {

    @Autowired
    private CompanyDao dao;

    @Autowired
    private CreditDao creditDao;

    @Autowired
    private CustomerstatusDao customerstatusDao;

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private ActivitylogDao activitylogDao;

    @Autowired
    private ActivitytypeDao activitytypeDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/getcustomer", method = RequestMethod.GET,params = "number",produces = "application/json")
    public Company company(@RequestParam("number") String number) {
        Company company;
        company = dao.findByNumber(number);
        return company;
    }

    @RequestMapping(value = "/companys/nextnumber", method = RequestMethod.GET, produces = "application/json")
    public String customernumber() {
        Scanner number = new Scanner(dao.numbermax()).useDelimiter("[^0-9]+");
        int intnumber = number.nextInt()+1;
        return  "CUS-"+intnumber;
    }


    @RequestMapping(value = "/companys", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Company> finAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size){

        if(AuthProvider.isUser(username,password)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    @RequestMapping(value = "/companys/list",method = RequestMethod.GET, produces = "application/json")
    public List<Company> customer(){return dao.list();}

    //ADD

    @RequestMapping(value = "/companys",method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Company company){

        if(AuthProvider.isAuthorized(username,password,ModuleList.COMPANY,AuthProvider.INSERT)) {
            Company cusname = dao.findByName(company.getName());
            Company cusnumber = dao.findByNumber(company.getNumber());
            if (cusname != null)
                return "Error-Validation : Name Exists";
            else if (cusnumber != null)
                return "Error-Validation : Number Exists";
            else
                try {
                    Customerstatus customerstatus = new Customerstatus();
                    customerstatus.setId(1);
                    company.setCustomerstatusId(customerstatus);
                    dao.save(company);
                    Credit credit = new Credit();
                    credit.setValue(BigDecimal.valueOf(0.00));
                    credit.setMaxvalue(BigDecimal.valueOf(100000.00));
                    credit.setCompanyId(company);
                    creditDao.save(credit);

                    activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.COMPANY.toString()),activitytypeDao.findByName("ADD"),company.getName()+" - "+company.getNumber()));


                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

        }


    @RequestMapping(value = "/companys", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Company company){


        if(AuthProvider.isAuthorized(username,password,ModuleList.COMPANY,AuthProvider.UPDATE)) {
            Company cus = dao.findByNumber(company.getNumber());
            if(cus==null || cus.getId()== company.getId()) {
                try {
                    dao.save(company);
                    activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.COMPANY.toString()),activitytypeDao.findByName("UPDATE"),company.getName()+" - "+company.getNumber()));
                    return "0";
                }
                catch(Exception e) {
                    return "Error-Updating : "+e.getMessage();
                }
            }
            else {  return "Error-Updating : Number Exists"; }
        }
        return "Error-Saving : You have no Permission";
    }

    @RequestMapping(value = "/companys", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Company company){
        if(AuthProvider.isAuthorized(username,password,ModuleList.COMPANY,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(company.getId()));
                activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.COMPANY.toString()),activitytypeDao.findByName("DELETE"),company.getName()+" - "+company.getNumber()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }


    //search

    @RequestMapping(value = "/companys", params = {"page", "size","name","number","statusid"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Company> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("name") String name, @RequestParam("number") String number, @RequestParam("statusid") Integer statusid) {

         System.out.println(name+"-"+number+"-"+statusid);


        if(AuthProvider.isAuthorized(username,password, ModuleList.COMPANY,AuthProvider.SELECT)) {

            List<Company> companies = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Company> customerstream = companies.stream();
            customerstream = customerstream.filter(e -> e.getName().startsWith(name));
            customerstream = customerstream.filter(e -> e.getNumber().startsWith(number));

            if (statusid != null)
                customerstream = customerstream.filter(e -> e.getCustomerstatusId().equals(customerstatusDao.getOne(statusid)));

            List<Company> customerstream2 = customerstream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < customerstream2.size() ? start + size : customerstream2.size();
            Page<Company> despage = new PageImpl<>(customerstream2.subList(start, end), PageRequest.of(page, size), customerstream2.size());

            return despage;
        }

        return null;

    }



}
