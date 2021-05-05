package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Dashboard;
import com.wajiraenterprises.entity.Designation;
import com.wajiraenterprises.entity.Employee;
import com.wajiraenterprises.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface EmployeeDao extends JpaRepository<Employee, Integer> {

    @Query(value="SELECT new Employee(e.id,e.callingname) FROM Employee e")
    List<Employee> list();

    @Query(value="SELECT new Employee(e.id,e.callingname) FROM Employee e WHERE e not in (Select u.employeeId from User u)")
    List<Employee> listWithoutUsers();

    @Query(value="SELECT new Employee(e.id,e.callingname) FROM Employee e WHERE e in (Select u.employeeId from User u)")
    List<Employee> listWithUseraccount();

    @Query("SELECT e FROM Employee e ORDER BY e.id DESC")
    Page<Employee> findAll(Pageable of);


    @Query("SELECT e FROM Employee e WHERE e.nic= :nic")
    Employee findByNIC(@Param("nic")String nic);

    @Query("SELECT e FROM Employee e WHERE e.number= :number")
    Employee findByNumber(@Param("number")String number);

    //SELECT * FROM wajiraenterprises.employee where designation_id=6 and id not in (select driveremployee from wajiraenterprises.vehicle);
/*    @Query(value="SELECT new Employee (e.id,e.callingname) FROM Employee e WHERE e.designationId.id=6 AND e.id NOT IN (SELECT driveremployee FROM Vehicle v)")
    List<Employee> AvailableDriverlist();*/

    //SELECT * FROM wajiraenterprises.employee where designation_id=7 and id not in (select drivingassistantemployee from wajiraenterprises.vehicle);
/*    @Query(value="SELECT new Employee (e.id,e.callingname) FROM Employee e WHERE e.designationId.id=7 AND e.id NOT IN (SELECT drivingassistantemployee FROM Vehicle v)")
    List<Employee> AvailableDriverAssistantlist();*/

   /* @Query(value="SELECT new Employee (e.id,e.callingname) FROM Employee e WHERE e.designationId.id=6")
    List<Employee> AllDriverlist();*/

    /*@Query(value="SELECT new Employee (e.id,e.callingname) FROM Employee e WHERE e.designationId.id=7")
    List<Employee> AllDriverAssistantlist();*/

    @Query(value="SELECT new Employee (e.id,e.callingname) FROM Employee e where e.designationId.id=:designationId")
    List<Employee> listByDesignation( @Param("designationId") Integer designationId);

    @Query(value="SELECT new Employee (e.id,e.callingname) FROM Employee e where e.designationId.id=:designationId")
    Employee getByDesignation( @Param("designationId") Integer designationId);

    //SELECT count(emp.designation_id), des.name FROM wajiraenterprises.employee as emp, designation as des where emp.designation_id = des.id;

    //count designation
    //SELECT designation_id, count(designation_id) from employee group by designation_id;
  /*  @Query( value = "SELECT COUNT(designation_id) FROM employee GROUP by designation_id",nativeQuery = true)
    Integer[] countEmployeesByDesignationId();*/

  //SELECT * FROM wajiraenterprises.employee where designation_id = 7 and id not in ( select driver from wajiraenterprises.deliveryconfirmation where id in ( select deliveryconfirmation_id from wajiraenterprises.delivery where deliverystatus_id = 2 ) );
        @Query(value="SELECT new Employee (e.id,e.callingname) FROM Employee e WHERE e.designationId.id=6 AND e.id NOT IN ( SELECT d.driver.id FROM Deliveryconfirmation d WHERE d.id in ( SELECT dl.deliveryconfirmationId FROM Delivery  dl where dl.deliverystatusId.id = 1))")
    List<Employee> AvailableDriverlist();

    @Query(value="SELECT new Employee (e.id,e.callingname) FROM Employee e WHERE e.designationId.id=7 AND e.id NOT IN ( SELECT d.driver.id FROM Deliveryconfirmation d WHERE d.id in ( SELECT dl.deliveryconfirmationId FROM Delivery  dl where dl.deliverystatusId.id = 1))")
    List<Employee> AvailableDriverAssistantlist();



    @Query(value="SELECT MAX (e.number) from Employee e")
    String numbermax();



    /*List<Employee> countEmployeesByDesignationIdIs(Designation designation);*/

    //


    /*
    @Query("SELECT e FROM Employee e WHERE e.employeestatusId.id= :employeestatusid")
    Page<Employee> findAllByEmployeestatus(Pageable of, @Param("employeestatusid")Integer employeestatusid);

    @Query("SELECT e FROM Employee e WHERE e.designationId.id= :designationid")
    Page<Employee> findAllByDesignation(Pageable of, @Param("designationid")Integer designationid);

    @Query("SELECT e FROM Employee e WHERE e.designationId.id= :designationid AND e.employeestatusId.id= :employeestatusid")
    Page<Employee> findAllByDesignationEmployeestaus(Pageable of, @Param("designationid")Integer designationid, @Param("employeestatusid")Integer employeestatusid);

    @Query("SELECT e FROM Employee e WHERE e.fullname like :name AND e.nic like :nic")
    Page<Employee> findAllByNameNIC(Pageable of, @Param("name")String name, @Param("nic")String nic);

    @Query("SELECT e FROM Employee e WHERE e.fullname like :name AND e.nic like :nic AND e.designationId.id= :designationid")
    Page<Employee> findAllByNameNICDesignation(Pageable of, @Param("name")String name, @Param("nic")String nic, @Param("designationid")Integer designationid);

    @Query("SELECT e FROM Employee e WHERE e.fullname like :name AND e.nic like :nic AND e.employeestatusId.id= :employeestatusid")
    Page<Employee> findAllByNameNICEmployeestatus(Pageable of, @Param("name")String name, @Param("nic")String nic, @Param("employeestatusid")Integer employeestatusid);

    @Query("SELECT e FROM Employee e WHERE e.fullname like :name AND e.nic like :nic AND e.designationId.id= :designationid AND e.employeestatusId.id= :employeestatusid")
    Page<Employee> findAllByNameNICDesignationEmployeestatus(Pageable of, @Param("name")String name, @Param("nic")String nic, @Param("designationid")Integer designationid, @Param("employeestatusid")Integer employeestatusid);


*/



}

