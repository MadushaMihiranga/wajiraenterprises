package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Clearingprocess;
import com.wajiraenterprises.entity.Cusdec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CusdecDao extends JpaRepository<Cusdec,Integer> {

    @Query(value = "SELECT c FROM Cusdec c ORDER BY c.id DESC")
    Page<Cusdec> findAll(Pageable of);

    @Query("SELECT c FROM Cusdec c WHERE c.shipmentId.number= :number")
    Cusdec findByNumber(@Param("number")String number);

    @Query("SELECT c FROM Cusdec c WHERE c.clearingprocessId= :clearingprocessId")
    Cusdec findByClearingProcessno(@Param("clearingprocessId") Clearingprocess clearingprocessId);

}
