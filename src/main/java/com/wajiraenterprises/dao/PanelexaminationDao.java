package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Clearingprocess;
import com.wajiraenterprises.entity.Cusdec;
import com.wajiraenterprises.entity.Panelexamination;
import com.wajiraenterprises.entity.Shipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PanelexaminationDao extends JpaRepository<Panelexamination,Integer> {

    @Query(value = "SELECT p FROM Panelexamination p ORDER BY p.id DESC")
    Page<Panelexamination> findAll(Pageable of);

    @Query("SELECT p FROM Panelexamination p WHERE p.clearingprocessId= :clearingprocessId")
    Panelexamination findByClearingProcessno(@Param("clearingprocessId") Clearingprocess clearingprocessId);


    @Query("SELECT p FROM Panelexamination p WHERE p.shipmentId.number= :number")
    Panelexamination findByShipmentNo(@Param("number") String number);


}
