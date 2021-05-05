package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Vehiclerentelcompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehiclerentelcompanyDao extends JpaRepository<Vehiclerentelcompany,Integer> {

    @Query(value = "SELECT d FROM Vehiclerentelcompany d ORDER BY d.id DESC")
    Page<Vehiclerentelcompany> findAll(Pageable of);

    @Query(value="SELECT new Vehiclerentelcompany (v.id,v.name) FROM Vehiclerentelcompany v")
    List<Vehiclerentelcompany> list();

    @Query("SELECT v FROM Vehiclerentelcompany v WHERE v.name= :name")
    Vehiclerentelcompany findByName(@Param("name")String name);


}
