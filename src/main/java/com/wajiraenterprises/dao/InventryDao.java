package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Inventry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InventryDao extends JpaRepository<Inventry,Integer> {

    @Query("SELECT i FROM Inventry i ORDER BY i.id DESC")
    Page<Inventry> findAll(Pageable of);

    @Query(value="SELECT MAX (i.number) from Inventry i")
    String numbermax();

    @Query("SELECT i FROM Inventry i WHERE i.grnhasitemId.itemId.id= :id")
    Inventry findByItemId(@Param("id") Integer id);



    @Query(value="SELECT new Inventry (i.id, i.number,i.storedqty,i.storedate,i.discpatcheddate,i.grnhasitemId,i.invetryitemstatusId) FROM Inventry i where i.grnhasitemId.itemId.shipmentId.number=:number")
    List<Inventry> listByShipmentNumber(@Param("number") String number);

}
