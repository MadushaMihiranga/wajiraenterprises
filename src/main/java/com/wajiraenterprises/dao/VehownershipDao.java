package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Vehownership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VehownershipDao extends JpaRepository<Vehownership,Integer> {

    @Query(value="SELECT new Vehownership (v.id,v.name) FROM Vehownership v")
    List<Vehownership> list();


}
