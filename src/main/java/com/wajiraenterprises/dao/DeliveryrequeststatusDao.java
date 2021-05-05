package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Deliveryrequeststatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeliveryrequeststatusDao extends JpaRepository<Deliveryrequeststatus,Integer> {

    @Query(value="SELECT new Deliveryrequeststatus(d.id,d.name) FROM Deliveryrequeststatus d")
    List<Deliveryrequeststatus> list();

}
