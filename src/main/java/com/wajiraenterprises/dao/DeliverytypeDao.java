package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Deliverytype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeliverytypeDao extends JpaRepository<Deliverytype,Integer> {


    @Query(value="SELECT new Deliverytype (d.id,d.name) FROM Deliverytype d")
    List<Deliverytype> list();

}
