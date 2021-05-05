package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.*;
import com.wajiraenterprises.entity.Clearingprocess;
import com.wajiraenterprises.entity.Clearingprocessstatus;
import com.wajiraenterprises.entity.Collectdo;
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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class CollectdoController {

    @Autowired
    private CollectdoDao dao;

    @Autowired
    private ClearingprocessDao clearingprocessDao;

    @Autowired
    private ClearingprocessstatusDao clearingprocessstatusDao;

    @Autowired
    private ShipmentDao shipmentDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private DeliveryagentDao deliveryagentDao;

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private ActivitylogDao activitylogDao;

    @Autowired
    private ActivitytypeDao activitytypeDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/collecetdo", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Collectdo> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size){
        //System.out.println("Shipment Findall");


        if(AuthProvider.isAuthorized(username,password, ModuleList.CLEARINGPROCESS,AuthProvider.SELECT)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    /**Save**/

    @RequestMapping(value = "/collecetdo", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Collectdo collectdo) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.CLEARINGPROCESS,AuthProvider.INSERT)) {
            Collectdo clearinprocessno = dao.findByClearingProcessno(collectdo.getClearingprocessId());

            if ( clearinprocessno!= null){
                System.out.println("Delivery order for this shipment is already added");
                return "Delivery order for this shipment is already added";
            }

            else{
                try {
                    String number = collectdo.getClearingprocessId().getShipmentId().getNumber();
                    Clearingprocess clearingprocess;
                    Clearingprocessstatus clearingprocessstatus = new Clearingprocessstatus();
                    clearingprocessstatus.setId(3);
                    clearingprocess = clearingprocessDao.findByNumber(collectdo.getClearingprocessId().getShipmentId().getNumber());
                    clearingprocess.setClearingprocessstatusId(clearingprocessstatus);
                    clearingprocessDao.save(clearingprocess);
                    dao.save(collectdo);
                    activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.DELIVERYORDER.toString()),activitytypeDao.findByName("ADD"),"for Shipment No : "+number));

                    System.out.println("collectdo");
                    return "0";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error-Saving : " + e.getMessage();
                }
            }

        }
        return "Error-Saving : You have no Permission";
    }

    @RequestMapping(value = "/collecetdo", params = {"page", "size","number","customerid","deliveragentid"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Collectdo> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("number") String number, @RequestParam("customerid") Integer customerid, @RequestParam("deliveragentid") Integer deliveragentid) {
        //&number=&modsid=&customerid=&clearingclerkid=
       // System.out.println("Number-"+number+" / customer-"+customerid+" / deliveryagent-"+deliveragentid);
       // System.out.println("SEARCH DO");

        if(AuthProvider.isAuthorized(username,password, ModuleList.CLEARINGPROCESS,AuthProvider.SELECT)) {

            List<Collectdo> collectdos = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Collectdo> collectdoStream = collectdos.stream();

            collectdoStream = collectdoStream.filter(s -> s.getClearingprocessId().getShipmentId().getNumber().contains(number));

            if (customerid != null) {collectdoStream = collectdoStream.filter(s -> s.getClearingprocessId().getShipmentId().getCompanyId().equals(companyDao.getOne(customerid)));}

            if (deliveragentid != null) {collectdoStream = collectdoStream.filter(c -> c.getDeliveryagentId().equals(deliveryagentDao.getOne(deliveragentid)));}

            List<Collectdo> collectdoStream2 = collectdoStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < collectdoStream2.size() ? start + size : collectdoStream2.size();
            Page<Collectdo> despage = new PageImpl<>(collectdoStream2.subList(start, end), PageRequest.of(page, size), collectdoStream2.size());
            //System.out.println("searchresult : "+despage );

            return despage;
        }

        return null;
    }

}
