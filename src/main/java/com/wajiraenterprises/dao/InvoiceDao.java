package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Inventry;
import com.wajiraenterprises.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceDao extends JpaRepository<Invoice,Integer> {

    @Query("SELECT i FROM Invoice i ORDER BY i.id DESC")
    Page<Invoice> findAll(Pageable of);

    @Query(value="SELECT MAX (i.number) from Invoice i")
    String numbermax();

    @Query("SELECT i FROM Invoice i WHERE i.shipmentId.number= :shpnumber")
    Invoice findByShipmentNumber(@Param("shpnumber") String shpnumber);




}
