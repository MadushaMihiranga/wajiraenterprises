package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Deliveryrequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeliveryrequestDao extends JpaRepository<Deliveryrequest,Integer> {

    @Query(value = "SELECT d FROM Deliveryrequest d ORDER BY d.id DESC")
    Page<Deliveryrequest> findAll(Pageable of);

    @Query(value="SELECT MAX (d.number) from Deliveryrequest d")
    String numbermax();

    @Query("SELECT d FROM Deliveryrequest d WHERE d.shipmentId.number= :number")
    Deliveryrequest findByShipment(@Param("number")String number);

    @Query("SELECT d FROM Deliveryrequest d WHERE d.number= :number")
    Deliveryrequest findbyNumber(@Param("number")String number);


}
