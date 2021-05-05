package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleDao extends JpaRepository<Vehicle,Integer> {

    @Query(value = "SELECT v FROM Vehicle v ORDER BY v.id DESC")
    Page<Vehicle> findAll(Pageable of);

    @Query("SELECT v FROM Vehicle v WHERE v.rgno= :rgno")
    Vehicle findByRgNo(@Param("rgno")String rgno);

    @Query("SELECT v FROM Vehicle v WHERE v.engno= :engno")
    Vehicle findByEngNo(@Param("engno")String engno);

    @Query("SELECT v FROM Vehicle v WHERE v.chaseno= :chaseno")
    Vehicle findByChaseNo(@Param("chaseno")String chaseno);

    @Query("SELECT v FROM Vehicle v WHERE v.number= :number")
    Vehicle findByNumber(@Param("number")String number);

    //SELECT max(cast(number as signed)) FROM wajiraenterprises.vehicle;
    @Query(value="SELECT MAX (v.number) from Vehicle v")
    String numbermax();

    //    @Query(value="SELECT new Employee (e.id,e.callingname) FROM Employee e WHERE e.designationId.id=6 AND e.id NOT IN (SELECT driveremployee FROM Vehicle v)")




    @Query(value="SELECT new Vehicle (v.id,v.number,v.modelId,v.rgno) FROM Vehicle v where v.vehicletypeId.id=:vehicletypeId and v.statusId.id = 1")
    List<Vehicle> findAvailableVehicleByType( @Param("vehicletypeId") Integer vehicletypeId);

    //@Query(value = "SELECT new Vehicle (v.id,v.regno) FROM Vehicle v")
    //List<Vehicle> list();

}
