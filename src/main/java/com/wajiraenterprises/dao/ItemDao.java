package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemDao extends JpaRepository<Item,Integer> {

    @Query(value="SELECT new Item (i.id,i.description,i.qty,i.packagetypeId) FROM Item i where i.shipmentId.number=:number")
    List<Item> listByShipmentNumber(@Param("number") String number);

}
