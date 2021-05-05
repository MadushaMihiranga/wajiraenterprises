package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Deliverymethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeliverymethodDao extends JpaRepository<Deliverymethod,Integer> {

    @Query(value="SELECT new Deliverymethod (d.id,d.name) FROM Deliverymethod d")
    List<Deliverymethod> list();

}
