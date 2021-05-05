package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Clearingprocess;
import com.wajiraenterprises.entity.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryDao extends JpaRepository<Delivery,Integer> {

    @Query("SELECT d FROM Delivery d ORDER BY d.id DESC")
    Page<Delivery> findAll(Pageable of);

    @Query(value="SELECT MAX (d.number) from Delivery d")
    String numbermax();

    @Query("SELECT d FROM Delivery d WHERE d.number= :number")
    Delivery findByNumber(@Param("number")String number);

    @Query("SELECT d FROM Delivery d WHERE d.deliveryconfirmationId.deliveryrequestId.shipmentId.number= :shipmentnumber")
    List <Delivery> findByShipmentNumber(@Param("shipmentnumber")String shipmentnumber);

}
