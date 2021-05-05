package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.*;
import com.wajiraenterprises.entity.Employee;

import com.wajiraenterprises.services.EmailService;
import com.wajiraenterprises.services.PasswordResetHandler;
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
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeDao dao;

    @Autowired
    private DesignationDao daoDesignation;

    @Autowired
    private EmployeestatusDao daoEmployeestatus;

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private ActivitylogDao activitylogDao;

    @Autowired
    private ActivitytypeDao activitytypeDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private EmailService emailService;


    @RequestMapping(value = "/employee/nextnumber", method = RequestMethod.GET, produces = "application/json")
    public String employeeenumber() {
        Scanner number = new Scanner(dao.numbermax()).useDelimiter("[^0-9]+");
        int intnumber = number.nextInt()+1;
        return  "EMP-"+intnumber;
    }

    /**Employee List By Designation**/
    @RequestMapping(value = "/employee/listbydesgnation",params ={"designationId",},method = RequestMethod.GET,produces = "application/json")
    public List<Employee> listByDesignation(@RequestParam("designationId") Integer designationId) {
        return dao.listByDesignation(designationId);
    }

    @RequestMapping(value = "/employees/list", method = RequestMethod.GET, produces = "application/json")
    public List<Employee> list(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return dao.list();
        }
        return null;
    }


   @RequestMapping(value = "/employees/drivers/listavailable", method = RequestMethod.GET,produces = "application/json")
    public List<Employee> driverlist(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return dao.AvailableDriverlist();
        }
        return null;
    }

  /*  @RequestMapping(value = "/employees/alldrivers/list", method = RequestMethod.GET,produces = "application/json")
    public List<Employee> alldriverlist(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return dao.AllDriverlist();
        }
        return null;
    }*/


    @RequestMapping(value = "/employees/driverassistant/availablelist", method = RequestMethod.GET,produces = "application/json")
    public List<Employee> driverassistant(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return dao.AvailableDriverAssistantlist();
        }
        return null;
    }

  /*  @RequestMapping(value = "/employees/alldriverassistant/list", method = RequestMethod.GET,produces = "application/json")
    public List<Employee> alldriverassistant(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return dao.AllDriverAssistantlist();
        }
        return null;
    }*/




    @RequestMapping(value = "/employees/list/withoutusers",  method = RequestMethod.GET, produces = "application/json")
    public List<Employee> listwithoutusers(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
        return dao.listWithoutUsers();
        }
        return null;
    }

    @RequestMapping(value = "/employees/list/withuseraccount",  method = RequestMethod.GET, produces = "application/json")
    public List<Employee> listwithuseraccount(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
        return dao.listWithUseraccount();
        }
        return null;
    }




    @RequestMapping(value = "/employees", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Employee> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password,@RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.EMPLOYEE,AuthProvider.SELECT)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    @RequestMapping(value = "/employees", params = {"page", "size","name","nic","designationid","employeestatusid"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Employee> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("name") String name, @RequestParam("nic") String nic, @RequestParam("designationid") Integer designationid, @RequestParam("employeestatusid") Integer employeestatusid) {

       // System.out.println(name+"-"+nic+"-"+designationid+"-"+employeestatusid);


        if(AuthProvider.isAuthorized(username,password, ModuleList.EMPLOYEE,AuthProvider.SELECT)) {

            List<Employee> employees = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Employee> employeestream = employees.stream();
            employeestream = employeestream.filter(e -> !(e.getCallingname().equals("Admin")));

            if (designationid != null)
                employeestream = employeestream.filter(e -> e.getDesignationId().equals(daoDesignation.getOne(designationid)));
            if (employeestatusid != null)
                employeestream = employeestream.filter(e -> e.getEmployeestatusId().equals(daoEmployeestatus.getOne(employeestatusid)));
            employeestream = employeestream.filter(e -> e.getNic().startsWith(nic));
            employeestream = employeestream.filter(e -> e.getFullname().contains(name));

            List<Employee> employees2 = employeestream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < employees2.size() ? start + size : employees2.size();
            Page<Employee> emppage = new PageImpl<>(employees2.subList(start, end), PageRequest.of(page, size), employees2.size());

            return emppage;
        }

        return null;

    }


    @RequestMapping(value = "/employees", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Employee employee) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.EMPLOYEE,AuthProvider.INSERT)) {
            Employee empnic = dao.findByNIC(employee.getNic());
            Employee empnumber = dao.findByNumber(employee.getNumber());
            if (empnic != null)
                return "Error-Validation : NIC Exists";
            else if (empnumber != null)
                return "Error-Validation : Number Exists";
            else
                try {
                    dao.save(employee);

                    String message = "Dear "+employee.getCallingname()+"\n\n" +
                            " Karunadasa learners gives the best possible start to a safe life time start driving. We are in the business since 1960 in providing driving lessons. ";

                   // emailService.sendStudentEmail("madusha19970126@gmail.com","Welcome to Karunadasa Learners",message);

                    activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.EMPLOYEE.toString()),activitytypeDao.findByName("ADD"),employee.getCallingname()+" - ( "+employee.getNumber()+" )"));
                    return "0";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }



    @RequestMapping(value = "/employees", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Employee employee) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.EMPLOYEE,AuthProvider.UPDATE)) {
        Employee emp = dao.findByNIC(employee.getNic());
        if(emp==null || emp.getId()==employee.getId()) {
            try {
                dao.save(employee);
                activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.EMPLOYEE.toString()),activitytypeDao.findByName("UPDATE"),employee.getCallingname()+" - ( "+employee.getNumber()+" )"));
                return "0";
            }
            catch(Exception e) {
                e.printStackTrace();
                return "Error-Updating : "+e.getMessage();
            }
        }
        else {  return "Error-Updating : NIC Exists"; }
        }
        return "Error-Updating : You have no Permission";
    }


    @RequestMapping(value = "/employees", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Employee employee ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.EMPLOYEE,AuthProvider.DELETE)) {
        try {
            dao.delete(dao.getOne(employee.getId()));
            System.out.println("delete-Employee : [ name : "+employee.getCallingname()+", id : "+employee.getId()+" ]");
            return "0";
        }
        catch(Exception e) {
            return "Error-Deleting : "+e.getMessage();
        }
    }
        return "Error-Deleting : You have no Permission";

    }






}
