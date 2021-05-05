package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Deliveryterms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeliverytermsDao extends JpaRepository<Deliveryterms,Integer> {

    @Query(value="SELECT new Deliveryterms(d.id,d.term,d.description) FROM Deliveryterms d")
    List<Deliveryterms> list();

}
