package com.wajiraenterprises.dao;


import com.wajiraenterprises.entity.Shippingcompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShippingcompanyDao extends JpaRepository<Shippingcompany,Integer> {

    @Query(value="SELECT new Shippingcompany (s.id,s.name,s.address) FROM Shippingcompany s")
    List<Shippingcompany> list();

    @Query("SELECT s FROM Shippingcompany s WHERE s.name= :name")
    Shippingcompany findByName(@Param("name")String name);

    @Query("SELECT s FROM Shippingcompany s WHERE s.address= :address")
    Shippingcompany findByAddress(@Param("address")String address);

    @Query("SELECT s FROM Shippingcompany s WHERE s.contactno= :contactno")
    Shippingcompany findByContactNo(@Param("contactno")String contactno);

    @Query("SELECT s FROM Shippingcompany s WHERE s.email= :email")
    Shippingcompany findByEmail(@Param("email")String email);

}
