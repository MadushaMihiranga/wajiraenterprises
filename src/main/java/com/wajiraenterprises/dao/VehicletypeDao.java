package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Vehicletype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicletypeDao extends JpaRepository<Vehicletype,Integer> {

    @Query(value="SELECT new Vehicletype (v.id,v.name) FROM Vehicletype v")
    List<Vehicletype> list();

    @Query("SELECT v FROM Vehicletype v WHERE v.name= :name")
    Vehicletype findByName(@Param("name")String name);

}
