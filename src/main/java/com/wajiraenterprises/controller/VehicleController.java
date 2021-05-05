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
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class VehicleController {

    @Autowired
    private VehicleDao dao;

    @Autowired
    private VehiclestatusDao vehiclestatusDao;

    @Autowired
    private VehicletypeDao vehicletypeDao;

    @Autowired
    private  EmployeeDao employeeDao;

    @Autowired
    private ModelDao modelDao;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private ActivitylogDao activitylogDao;

    @Autowired
    private ActivitytypeDao activitytypeDao;

    @Autowired
    private UserDao userDao;

    /****************************VEHICLE DATA LISTS***************************************/
    /*************************************************************************************/

    /**** VehicleStatus List ****/
    @RequestMapping(value = "/vehiclestatus/list", method = RequestMethod.GET, produces = "application/json")
    public List<Vehiclestatus> vehiclestatuse() { return vehiclestatusDao.list(); }

    /*************************************************************************************/
    /*************************************************************************************/

    @RequestMapping(value = "/vehicle/nextnumber", method = RequestMethod.GET, produces = "application/json")
    public String vehiclenumber() {

     /*   int intnumber;
        String num;
        if (dao.numbermax().substring(0,2)==Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2)){
            Scanner number = new Scanner(dao.numbermax().substring(0,2)).useDelimiter("[^0-9]+");
            intnumber = number.nextInt()+1;
            num = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2)+intnumber;
        }else {
            num = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2)+"0001";
        }

        System.out.println(num);
        return  "VEH-"+num;*/

        Scanner number = new Scanner(dao.numbermax()).useDelimiter("[^0-9]+");
        int intnumber = number.nextInt()+1;
        return  "VEH-"+intnumber;

    }


    /**Employee List By Designation**/
    @RequestMapping(value = "/vehicle/listbytype",params ={"vehicletypeId",},method = RequestMethod.GET,produces = "application/json")
    public List<Vehicle> listByVehType(@RequestParam("vehicletypeId") Integer vehicletypeId) {
        return dao.findAvailableVehicleByType(vehicletypeId);
    }



    @RequestMapping(value = "/vehicles", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Vehicle> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size){

        if(AuthProvider.isUser(username,password)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    //ADD
    @RequestMapping(value = "/vehicles",method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Vehicle vehicle){



        if(AuthProvider.isAuthorized(username,password, ModuleList.VEHICLE,AuthProvider.INSERT)) {
            Vehicle vehregno  = dao.findByRgNo(vehicle.getRgno());
            Vehicle vehengno  = dao.findByEngNo(vehicle.getEngno());
            Vehicle vehchano  = dao.findByChaseNo(vehicle.getChaseno());
            Vehicle vehnumber = dao.findByNumber(vehicle.getNumber());

            if (vehregno != null){
                return "Error-Validation : Vehicle Registration Number Exists";
            }
            else if (vehnumber != null){
                return "Error-Validation : Vehicle Number Exists";
            }
            else if (vehchano != null){
                return "Error-Validation : Chase Number Exists";
            }
            else if (vehengno != null){
                return "Error-Validation : Engine Number Exists";
            }
            else {
                try {

                    dao.save(vehicle);
                    activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.VEHICLE.toString()),activitytypeDao.findByName("ADD"),vehicle.getNumber()+" "+vehicle.getModelId().getName()+"-"+vehicle.getRgno())); //vehicle.getModelId().getBrandId().getName()+" "+vehicle.getModelId().getName()+"-"+vehicle.getRgno()

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


    //UPDATE
    @RequestMapping(value = "/vehicles", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Vehicle vehicle){

        if(AuthProvider.isAuthorized(username,password,ModuleList.VEHICLE,AuthProvider.UPDATE)) {
            Vehicle veh = dao.findByNumber(vehicle.getNumber());
            if(veh==null || veh.getId()==vehicle.getId()) {
                try {
                    dao.save(vehicle);
                    activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.VEHICLE.toString()),activitytypeDao.findByName("UPDATE"),vehicle.getNumber()+" "+vehicle.getModelId().getBrandId().getName()+" "+vehicle.getModelId().getName()+"-"+vehicle.getRgno()));

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


    //DELETE
    @RequestMapping(value = "/vehicles", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Vehicle vehicle){
        if(AuthProvider.isAuthorized(username,password,ModuleList.VEHICLE,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(vehicle.getId()));
                activitylogDao.save(Utilities.CreateActivityLog(userDao.findByUsername(username),moduleDao.findByName(ModuleList.VEHICLE.toString()),activitytypeDao.findByName("DELETE"),vehicle.getNumber()+" "+vehicle.getModelId().getBrandId().getName()+" "+vehicle.getModelId().getName()+"-"+vehicle.getRgno()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";
    }


    //search
    @RequestMapping(value = "/vehicles", params = {"page", "size","statusid","typeid","brandid","modelid"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Vehicle> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("statusid") Integer statusid, @RequestParam("typeid") Integer typeid,  @RequestParam("brandid") Integer brandid, @RequestParam("modelid") Integer modelid) {

        //System.out.println(statusid+" - "+typeid+" - "+driverid+" - "+" - "+assitantid+" - "+brandid+" - "+modelid);

        if(AuthProvider.isAuthorized(username,password, ModuleList.VEHICLE,AuthProvider.SELECT)) {

            List<Vehicle> vehicles = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Vehicle> vehiclestream = vehicles.stream();

            if (statusid != null) {vehiclestream = vehiclestream.filter(v -> v.getStatusId().equals(vehiclestatusDao.getOne(statusid)));}

            if (typeid != null) {vehiclestream = vehiclestream.filter(v -> v.getVehicletypeId().equals(vehicletypeDao.getOne(typeid)));}

            /*if (driverid != null) {vehiclestream = vehiclestream.filter(v -> v.getDriveremployee().equals(employeeDao.getOne(driverid)));}

            if (assitantid != null) {vehiclestream = vehiclestream.filter(v -> v.getDrivingassistantemployee().equals(employeeDao.getOne(assitantid)));}*/

            if (brandid != null) {vehiclestream = vehiclestream.filter(v -> v.getModelId().getBrandId().equals(brandDao.getOne(brandid)));}

            if (modelid != null) {vehiclestream = vehiclestream.filter(v -> v.getModelId().equals(modelDao.getOne(modelid)));}

            List<Vehicle> vehiclestream2 = vehiclestream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < vehiclestream2.size() ? start + size : vehiclestream2.size();
            Page<Vehicle> despage = new PageImpl<>(vehiclestream2.subList(start, end), PageRequest.of(page, size), vehiclestream2.size());
            //System.out.println("seardh123");

            return despage;
        }
        return null;
    }



}
