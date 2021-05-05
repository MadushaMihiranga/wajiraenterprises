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

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class PanelexaminationController {

    @Autowired
    private PanelexaminationDao dao;

    @Autowired
    private ClearingprocessDao clearingprocessDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private PestatusDao statusDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private ActivitylogDao activitylogDao;

    @Autowired
    private ActivitytypeDao activitytypeDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/panelexamination", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Panelexamination> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size){

        if(AuthProvider.isAuthorized(username,password, ModuleList.CLEARINGPROCESS,AuthProvider.SELECT)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    @RequestMapping(value = "/hasPanelExamination", method = RequestMethod.GET,params = "number",produces = "application/json")
    public Boolean haspanelexamination(@RequestParam("number") String number) {

        Panelexamination panelexamination = dao.findByShipmentNo(number);
        if (panelexamination != null){
            return true;
        }else {
            return false;
        }

    }

    @RequestMapping(value = "/getPanelExamination", method = RequestMethod.GET,params = "number",produces = "application/json")
    public Panelexamination getpanelexamination(@RequestParam("number") String number) {

        Panelexamination panelexamination = dao.findByShipmentNo(number);
        return panelexamination;
    }

    @RequestMapping(value = "/panelexamination", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Panelexamination panelexamination) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.CLEARINGPROCESS,AuthProvider.INSERT)) {
            Panelexamination clearinprocessno = dao.findByClearingProcessno(panelexamination.getClearingprocessId());

            if ( clearinprocessno!= null){
                System.out.println("Customs Declaration for this shipment is already added");
                return "Panel Examination Result for this shipment is already added";
            }

            else{
                try {

            Clearingprocess clearingprocess;

                Clearingprocessstatus clearingprocessstatus = new Clearingprocessstatus();
                if (panelexamination.getPestatusId().getId()==1){
                    clearingprocessstatus.setId(6);
                }else if (panelexamination.getPestatusId().getId()==3){
                    clearingprocessstatus.setId(10);
                }else if (panelexamination.getPestatusId().getId()==2){
                    clearingprocessstatus.setId(5);
                }
                clearingprocess = clearingprocessDao.findByNumber(panelexamination.getClearingprocessId().getShipmentId().getNumber());
                clearingprocess.setClearingprocessstatusId(clearingprocessstatus);
                System.out.println(clearingprocess.getClearingprocessstatusId().getId());
                if (clearingprocess.getClearingprocessstatusId().getId()==6){
                    clearingprocess.setEndate(LocalDate.now());
                }
                clearingprocessDao.save(clearingprocess);

                for (Examinationhascheckpoints checkpoint: panelexamination.getExaminationhascheckpointsList()) {
                    checkpoint.setPanelexaminationId(panelexamination);
                    //checkpoint.setPestatusId(pestatus);
                }


                    dao.save(panelexamination);
                    activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.PANELEXAMINATION.toString()),activitytypeDao.findByName("ADD"),"for Shipment No : "+panelexamination.getClearingprocessId().getShipmentId().getNumber()));

                    System.out.println("panelexamination");
                    return "0";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error-Saving : " + e.getMessage();
                }
            }
        }
        return "Error-Saving : You have no Permission";
    }


    @RequestMapping(value = "/panelexamination", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Panelexamination panelexamination) {


        if(AuthProvider.isAuthorized(username,password,ModuleList.CLEARINGPROCESS,AuthProvider.UPDATE)) {
            try {
                Clearingprocess clearingprocess;
                Clearingprocessstatus clearingprocessstatus = new Clearingprocessstatus();
                if (panelexamination.getPestatusId().getId()==1){
                    clearingprocessstatus.setId(6);
                }else if (panelexamination.getPestatusId().getId()==3){
                    clearingprocessstatus.setId(10);
                }else if (panelexamination.getPestatusId().getId()==2){
                    clearingprocessstatus.setId(5);
                }
                clearingprocess = clearingprocessDao.findByNumber(panelexamination.getClearingprocessId().getShipmentId().getNumber());
                clearingprocess.setClearingprocessstatusId(clearingprocessstatus);
                System.out.println(clearingprocess.getClearingprocessstatusId().getId());
                if (clearingprocess.getClearingprocessstatusId().getId()==6){
                    clearingprocess.setEndate(LocalDate.now());
                }
                clearingprocessDao.save(clearingprocess);

                for (Examinationhascheckpoints checkpoint: panelexamination.getExaminationhascheckpointsList()) {
                    checkpoint.setPanelexaminationId(panelexamination);
                    //checkpoint.setPestatusId(pestatus);
                }

                dao.save(panelexamination);
                activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.PANELEXAMINATION.toString()),activitytypeDao.findByName("UPDATE"),"for Shipment No : "+panelexamination.getClearingprocessId().getShipmentId().getNumber()));

                System.out.println("panelexamination");
                return "0";
            } catch (Exception e) {
                e.printStackTrace();
                return "Error-Saving : " + e.getMessage();
            }
        }
        else
            return "Error-Updating : You have no Permission";
    }


    @RequestMapping(value = "/panelexamination", params = {"page", "size","number","customerid","refno","locationid","statusid","clearingclerkid"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Panelexamination> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("number") String number, @RequestParam("customerid") Integer customerid, @RequestParam("locationid") Integer locationid , @RequestParam("refno") String refno , @RequestParam("statusid") Integer statusid, @RequestParam("clearingclerkid") Integer clearingclerkid) {
        //&number=&modsid=&customerid=&clearingclerkid=
        // System.out.println("Number-"+number+" / customer-"+customerid+" / deliveryagent-"+deliveragentid);
         //System.out.println("SEARCH PE");

        if(AuthProvider.isAuthorized(username,password, ModuleList.CLEARINGPROCESS,AuthProvider.SELECT)) {

            List<Panelexamination> collectdos = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Panelexamination> panelexaminationStream = collectdos.stream();

            panelexaminationStream = panelexaminationStream.filter(s -> s.getClearingprocessId().getShipmentId().getNumber().contains(number));

            panelexaminationStream = panelexaminationStream.filter(s -> s.getCusdecId().getCustomrefno().startsWith(refno));

            if (customerid != null) {panelexaminationStream = panelexaminationStream.filter(s -> s.getClearingprocessId().getShipmentId().getCompanyId().equals(companyDao.getOne(customerid)));}

            if (locationid != null) {panelexaminationStream = panelexaminationStream.filter(c -> c.getLocationId().equals(locationDao.getOne(locationid)));}

            if (statusid != null) {panelexaminationStream = panelexaminationStream.filter(c -> c.getPestatusId().equals(statusDao.getOne(statusid)));}

            if (clearingclerkid != null) {panelexaminationStream = panelexaminationStream.filter(c -> c.getEmployeeId().equals(employeeDao.getOne(clearingclerkid)));}

            List<Panelexamination> panelexaminationStream2 = panelexaminationStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < panelexaminationStream2.size() ? start + size : panelexaminationStream2.size();
            Page<Panelexamination> despage = new PageImpl<>(panelexaminationStream2.subList(start, end), PageRequest.of(page, size), panelexaminationStream2.size());
            //System.out.println("searchresult : "+despage );

            return despage;
        }

        return null;
    }

}
