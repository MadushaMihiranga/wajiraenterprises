package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.ActivitylogDao;
import com.wajiraenterprises.dao.ActivitytypeDao;
import com.wajiraenterprises.dao.EmployeeDao;
import com.wajiraenterprises.dao.ModuleDao;
import com.wajiraenterprises.entity.Activitylog;
import com.wajiraenterprises.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class ActivitylogController {

    @Autowired
    private ActivitylogDao activitylogDao;

    @Autowired
    private ActivitytypeDao activitytypeDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private ModuleDao moduleDao;

    @RequestMapping(value = "/activitylog", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Activitylog> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size){

        if(AuthProvider.isAuthorized(username,password, ModuleList.ACTIVITYLOG,AuthProvider.SELECT)) {
            return activitylogDao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    @RequestMapping(value = "/activitylog", params = {"page", "size","typeid","moduleid","employeeid"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Activitylog> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("typeid") Integer typeid, @RequestParam("moduleid") Integer moduleid, @RequestParam("employeeid") Integer employeeid) {

        // System.out.println(name+"-"+nic+"-"+designationid+"-"+employeestatusid);


        if(AuthProvider.isAuthorized(username,password, ModuleList.ACTIVITYLOG,AuthProvider.SELECT)) {

            List<Activitylog> activitylogs = activitylogDao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Activitylog> activitylogStream = activitylogs.stream();


            if (typeid != null)
                activitylogStream = activitylogStream.filter(e -> e.getActivitytypeId().equals(activitytypeDao.getOne(typeid)));
            if (moduleid != null)
                activitylogStream = activitylogStream.filter(e -> e.getModuleId().equals(moduleDao.getOne(moduleid)));
            if (employeeid != null)
                activitylogStream = activitylogStream.filter(e -> e.getUserId().getEmployeeId().equals(employeeDao.getOne(employeeid)));



            List<Activitylog> activitylogs2 = activitylogStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < activitylogs2.size() ? start + size : activitylogs2.size();
            Page<Activitylog> emppage = new PageImpl<>(activitylogs2.subList(start, end), PageRequest.of(page, size), activitylogs2.size());

            return emppage;
        }

        return null;

    }


}
