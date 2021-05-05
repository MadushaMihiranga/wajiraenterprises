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
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class CusdecController {

    @Autowired
    private CusdecDao cusdecDao;

    @Autowired
    private ClearingprocessDao clearingprocessDao;

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

    @RequestMapping(value = "/cusdec", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Cusdec> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size){

        if(AuthProvider.isUser(username,password)) {
            return cusdecDao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    @RequestMapping(value = "/hasCusDec", method = RequestMethod.GET,params = "shipmentno",produces = "application/json")
    public Boolean hasCusDec(@RequestParam("shipmentno") String shipmentno) {

        Cusdec cusdec = cusdecDao.findByNumber(shipmentno);
        if (cusdec != null){
            return true;
        }else {
            return false;
        }

    }


    @RequestMapping(value = "/getCusdec", method = RequestMethod.GET,params = "shipmentno",produces = "application/json")
    public Cusdec getCusDec(@RequestParam("shipmentno") String shipmentno) {
        Cusdec cusdec;
        cusdec = cusdecDao.findByNumber(shipmentno);
        System.out.println(cusdec.getAssessmentnumber());
        return cusdec;
    }



    @RequestMapping(value = "/cusdec", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Cusdec cusdec) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.CLEARINGPROCESS,AuthProvider.INSERT)) {
            Cusdec clearinprocessno = cusdecDao.findByClearingProcessno(cusdec.getClearingprocessId());

            if ( clearinprocessno!= null){
                System.out.println("Customs Declaration for this shipment is already added");
                return "Customs Declaration for this shipment is already added";
            }

            else{
                try {
                    Clearingprocess clearingprocess;
                    Clearingprocessstatus clearingprocessstatus = new Clearingprocessstatus();
                    clearingprocessstatus.setId(4);
                    clearingprocess = clearingprocessDao.findByNumber(cusdec.getClearingprocessId().getShipmentId().getNumber());
                    clearingprocess.setClearingprocessstatusId(clearingprocessstatus);
                    clearingprocessDao.save(clearingprocess);

                    for (Cusdechastax cusdectax: cusdec.getCusdechastaxList()) {
                        cusdectax.setCusdecId(cusdec);
                    }
                    cusdecDao.save(cusdec);
                    activitylogDao.save(Objects.requireNonNull(Utilities.CreateActivityLog(userDao.findByUsername(username), moduleDao.findByName(ModuleList.CUSDEC.toString()), activitytypeDao.findByName("ADD"), "for Shipment No : " + cusdec.getClearingprocessId().getShipmentId().getNumber())));
                    System.out.println("cusdec");
                    return "0";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error-Saving : " + e.getMessage();
                }
            }

        }
        return "Error-Saving : You have no Permission";
    }



    @RequestMapping(value = "/cusdec", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Cusdec cusdec) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.CLEARINGPROCESS,AuthProvider.UPDATE)) {

            try {

                 /*   Clearingprocess clearingprocess;
                    Clearingprocessstatus clearingprocessstatus = new Clearingprocessstatus();
                    clearingprocessstatus.setId(4);
                    clearingprocess = clearingprocessDao.findByNumber(cusdec.getClearingprocessId().getShipmentId().getNumber());
                    clearingprocess.setClearingprocessstatusId(clearingprocessstatus);
                    clearingprocessDao.save(clearingprocess);*/

                 for (Cusdechastax cusdectax: cusdec.getCusdechastaxList()) {
                     cusdectax.setCusdecId(cusdec);
                 }
                 cusdecDao.save(cusdec);
                activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.CUSDEC.toString()),activitytypeDao.findByName("UPDATE"),"for Shipment No : "+cusdec.getClearingprocessId().getShipmentId().getNumber()));
                System.out.println("cusdec");
                 return "0";
            } catch (Exception e) {
                return "Error-Saving : " + e.getMessage();
            }
        }else {
            return "Error-Saving : You have no Permission";
        }
    }



    @RequestMapping(value = "/cusdec", params = {"page", "size","number","customerid","refno","assessmentno"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Cusdec> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("number") String number, @RequestParam("customerid") Integer customerid, @RequestParam("refno") String refno, @RequestParam("assessmentno") String assessmentno) {
        //&number=&modsid=&customerid=&clearingclerkid=
         System.out.println("Number-"+number+" / customer-"+customerid+" / refno-"+refno+" assessmentno "+ assessmentno);
        // System.out.println("SEARCH DO");

        if(AuthProvider.isAuthorized(username,password, ModuleList.CLEARINGPROCESS,AuthProvider.SELECT)) {

            List<Cusdec> cusdec = cusdecDao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Cusdec> cusdecStream = cusdec.stream();

            cusdecStream = cusdecStream.filter(c -> c.getClearingprocessId().getShipmentId().getNumber().contains(number));

            if (customerid != null) {cusdecStream = cusdecStream.filter(c -> c.getClearingprocessId().getShipmentId().getCompanyId().equals(companyDao.getOne(customerid)));}

            cusdecStream = cusdecStream.filter(c -> c.getCustomrefno().contains(refno));

            cusdecStream = cusdecStream.filter(c -> c.getAssessmentnumber().contains(assessmentno));

            List<Cusdec> cusdecStream2 = cusdecStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < cusdecStream2.size() ? start + size : cusdecStream2.size();
            Page<Cusdec> despage = new PageImpl<>(cusdecStream2.subList(start, end), PageRequest.of(page, size), cusdecStream2.size());
            //System.out.println("searchresult : "+despage );

            return despage;
        }

        return null;
    }






}
