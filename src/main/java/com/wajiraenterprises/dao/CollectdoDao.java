package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Clearingprocess;
import com.wajiraenterprises.entity.Collectdo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CollectdoDao extends JpaRepository<Collectdo,Integer> {


    @Query("SELECT c FROM Collectdo c ORDER BY c.id DESC")
    Page<Collectdo> findAll(Pageable of);
    //findByClearingProcessno

    @Query("SELECT c FROM Collectdo c WHERE c.clearingprocessId= :clearingprocessId")
    Collectdo findByClearingProcessno(@Param("clearingprocessId") Clearingprocess clearingprocessId);


}
