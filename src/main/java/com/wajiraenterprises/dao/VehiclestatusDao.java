package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Vehiclestatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VehiclestatusDao extends JpaRepository<Vehiclestatus,Integer> {

    @Query(value="SELECT new Vehiclestatus (v.id,v.name) FROM Vehiclestatus v")
    List<Vehiclestatus> list();

}
