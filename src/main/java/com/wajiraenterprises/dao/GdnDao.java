package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Gdn;
import com.wajiraenterprises.entity.Inventry;
import com.wajiraenterprises.entity.Shipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GdnDao  extends JpaRepository<Gdn, Integer> {

    @Query("SELECT g FROM Gdn g ORDER BY g.id DESC")
    Page<Gdn> findAll(Pageable of);

    @Query(value="SELECT MAX (g.number) from Gdn g")
    String numbermax();

    @Query("SELECT g FROM Gdn g WHERE g.number= :number")
    Gdn findByNumber(@Param("number")String number);



}
