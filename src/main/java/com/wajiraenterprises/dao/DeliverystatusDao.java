package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Deliverystatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeliverystatusDao extends JpaRepository<Deliverystatus,Integer> {

    @Query(value="SELECT new Deliverystatus (d.id,d.name) FROM Deliverystatus d")
    List<Deliverystatus> list();

}
