package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Cheque;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChequeDao extends JpaRepository<Cheque,Integer> {

    @Query(value = "SELECT c FROM Cheque c ORDER BY c.id DESC")
    Page<Cheque> findAll(Pageable of);

    @Query("SELECT c FROM Cheque c WHERE c.chequenumber= :number")
    Cheque findByChequeNo(@Param("number") String number);



}
