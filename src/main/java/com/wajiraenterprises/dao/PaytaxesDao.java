package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Clearingprocess;
import com.wajiraenterprises.entity.Paytaxes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaytaxesDao extends JpaRepository<Paytaxes,Integer> {

    @Query(value = "SELECT p FROM Paytaxes p ORDER BY p.id DESC")
    Page<Paytaxes> findAll(Pageable of);

    @Query("SELECT p FROM Paytaxes p WHERE p.clearingprocessId= :clearingprocessId")
    Paytaxes findByClearingProcessno(@Param("clearingprocessId") Clearingprocess clearingprocessId);

    @Query("SELECT p FROM Paytaxes p WHERE p.cusdecId.shipmentId.number= :number")
    Paytaxes findByShipmentNo(@Param("number") String number);


}
