package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Deliveryconfirmation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryconfirmationDao extends JpaRepository<Deliveryconfirmation,Integer> {

    @Query(value = "SELECT d FROM Deliveryconfirmation d ORDER BY d.deliveryrequestId.id DESC")
    Page<Deliveryconfirmation> findAll(Pageable of);

    @Query("SELECT d FROM Deliveryconfirmation d WHERE d.deliveryrequestId.number= :number")
    Deliveryconfirmation findByNumber(@Param("number")String number);




}
