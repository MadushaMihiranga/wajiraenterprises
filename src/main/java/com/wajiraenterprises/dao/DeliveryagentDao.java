package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Deliveryagent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryagentDao extends JpaRepository<Deliveryagent,Integer> {

    @Query(value="SELECT new Deliveryagent(d.id,d.name,d.address,d.contactno) FROM Deliveryagent d")
    List<Deliveryagent> list();

    @Query("SELECT d FROM Deliveryagent d WHERE d.name= :name")
    Deliveryagent findByName(@Param("name")String name);

}
