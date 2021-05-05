package com.wajiraenterprises.dao;


import com.wajiraenterprises.entity.Bank;
import com.wajiraenterprises.entity.Shipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShipmentDao extends JpaRepository<Shipment,Integer> {

    @Query(value = "SELECT s FROM Shipment s ORDER BY s.id DESC")
    Page<Shipment> findAll(Pageable of);

    @Query(value="SELECT MAX (s.number) from Shipment s")
    String numbermax();

    @Query("SELECT s FROM Shipment s WHERE s.number= :number")
    Shipment findByNumber(@Param("number")String number);


    @Query(value="SELECT new Shipment (s.number) FROM Shipment s order by s.id desc ")
    List<Shipment> shipmentlist();



}
