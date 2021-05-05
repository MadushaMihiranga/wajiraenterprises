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

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class ShipmentController {

    @Autowired
    private ShipmentDao shipmentDao;

    @Autowired
    private ModsDao modsDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemHasLicenceDao itemHasLicenceDao;

    @Autowired
    private ClearingprocessDao clearingprocessDao;

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private ActivitylogDao activitylogDao;

    @Autowired
    private ActivitytypeDao activitytypeDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private NotificationDao notificationDao;


    @RequestMapping(value = "/shipment/list", method = RequestMethod.GET, produces = "application/json")
    public List<Shipment> shipments() {
        return shipmentDao.shipmentlist();
    }


    /*@Autowired
    private TransportdocumentsDao transportdocumentsDao;*/

    /********************************************Data Lists****************************************************************/

    @RequestMapping(value = "/mods/list", method = RequestMethod.GET, produces = "application/json")
    public List<Mods> mods() { return modsDao.list(); }

  /*  @RequestMapping(value = "/transportdocument/list", method = RequestMethod.GET, produces = "application/json")
    public List<Transportdocuments> transportdocuments() { return transportdocumentsDao.list(); }*/

    /**********************************************************************************************************************/

    /**Get Next Number**/
    @RequestMapping(value = "/shipment/nextnumber", method = RequestMethod.GET, produces = "application/json")
    public String shipmentnumber() {

        Scanner number1 = new Scanner(shipmentDao.numbermax()).useDelimiter("[^0-9]+");
        String num;
        if (Integer.toString(number1.nextInt()).substring(0,2).equals(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2))){
            Scanner number = new Scanner(shipmentDao.numbermax()).useDelimiter("[^0-9]+");
            num = Integer.toString(number.nextInt()+1);
        }else {
            num = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2)+"000001";
        }
            return  "SHP-"+num;
    }

    /**Findall Shipment**/
    @RequestMapping(value = "/shipments", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Shipment> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size){
        //System.out.println("Shipment Findall");

        if(AuthProvider.isUser(username,password)) {
            return shipmentDao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    /**Search Functions***/
    @RequestMapping(value = "/shipments", params = {"page", "size","number","modsid","customerid","clearingclerkid"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Shipment> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("number") String number, @RequestParam("modsid") Integer modsid, @RequestParam("customerid") Integer customerid, @RequestParam("clearingclerkid") Integer clearingclerkid) {
    //&number=&modsid=&customerid=&clearingclerkid=
        //System.out.println("Number-"+number+" / Mod-"+modsid+" / Customer-"+customerid+" - ClearingClerk-"+clearingclerkid);

        if(AuthProvider.isAuthorized(username,password, ModuleList.SHIPMENT,AuthProvider.SELECT)) {

            List<Shipment> shipments = shipmentDao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Shipment> shipmentStream = shipments.stream();

            shipmentStream = shipmentStream.filter(s -> s.getNumber().contains(number));

            if (modsid != null) {shipmentStream = shipmentStream.filter(s -> s.getModsId().equals(modsDao.getOne(modsid)));}

            if (customerid != null) {shipmentStream = shipmentStream.filter(s -> s.getCompanyId().equals(companyDao.getOne(customerid)));}

            if (clearingclerkid != null) {shipmentStream = shipmentStream.filter(s -> s.getClearingclerk().equals(employeeDao.getOne(clearingclerkid)));}

            List<Shipment> shipmentStream2 = shipmentStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < shipmentStream2.size() ? start + size : shipmentStream2.size();
            Page<Shipment> despage = new PageImpl<>(shipmentStream2.subList(start, end), PageRequest.of(page, size), shipmentStream2.size());
            //System.out.println("searchresult : "+despage );

            return despage;
        }

        return null;
    }

    /**Add Shipment**/
    @Transactional
    @RequestMapping(value = "/shipments",method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Shipment shipment){

        if(AuthProvider.isAuthorized(username,password, ModuleList.SHIPMENT,AuthProvider.INSERT)) {

            Shipment snumber  = shipmentDao.findByNumber(shipment.getNumber());

            if (snumber != null){
                return "Error-Validation : Shipment Registration Number Exists";
            }
            else {
                try {

                    for (Item shpitem: shipment.getItemList()){
                        for (ItemHasLicence itmlic: shpitem.getItemHasLicenceList()){
                            itmlic.setItemId(shpitem);
                        }
                        shpitem.setShipmentId(shipment);
                    }
                    for (Letterofcredit shplec: shipment.getLetterofcreditList()){
                        shplec.setShipmentId(shipment);
                    }

                    Clearingprocess clearingprocess =  new Clearingprocess();
                    Clearingprocessstatus clearingprocessstatus = new Clearingprocessstatus();
                    clearingprocessstatus.setId(2);
                    clearingprocess.setStartdate(shipment.getDateofregisterd());
                    clearingprocess.setShipmentId(shipment);
                    clearingprocess.setClearingprocessstatusId(clearingprocessstatus);

                    shipmentDao.save(shipment);
                    clearingprocessDao.save(clearingprocess);
                    activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.SHIPMENT.toString()),activitytypeDao.findByName("ADD"),shipment.getNumber()));
                    notificationDao.save(Utilities.CreateNotification("You have new shipment - "+shipment.getNumber(),1,shipment.getClearingclerk()));
                    return "0";
                }
                catch (Exception e){
                    e.printStackTrace();
                    return "Error-Saving : " + e.getMessage();
                }
            }
        }
        return "Error-Saving : You have no Permission";
    }

    /**Update Shipment**/
    @RequestMapping(value = "/shipments", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Shipment shipment) {


        if(AuthProvider.isAuthorized(username,password,ModuleList.SHIPMENT,AuthProvider.UPDATE)) {
            try {
                for (Item shpitem: shipment.getItemList()){
                    shpitem.setShipmentId(shipment);
                    for (ItemHasLicence itmlic: shpitem.getItemHasLicenceList()){
                        itmlic.setItemId(shpitem);
                    }
                }

                for (Letterofcredit shplec: shipment.getLetterofcreditList()){
                    shplec.setShipmentId(shipment);
                }
                shipmentDao.save(shipment);
                activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.SHIPMENT.toString()),activitytypeDao.findByName("UPDATE"),shipment.getNumber()));


                return "0";
            }
            catch(Exception e) {
                return "Error-Saving : " + e.getMessage();
            }
        }
        else
            return "Error-Updating : You have no Permission";
    }


    @Transactional
    @RequestMapping(value = "/shipments", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Shipment shipment ) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.SHIPMENT,AuthProvider.DELETE)) {
            try {
                Shipment shp = shipmentDao.getOne(shipment.getId());
                shp.getItemList().remove(shp);
                shp.getLetterofcreditList().remove(shp);
                shipmentDao.save(shp);
                shipmentDao.delete(shp);
                activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.SHIPMENT.toString()),activitytypeDao.findByName("DELETE"),shipment.getNumber()));

                return "0";

            } catch (Exception e) {
                return "Error-Deleting : " + e.getMessage();
            }
        }

        else
            return "Error-Deleting : You have no Permission";

    }


}
