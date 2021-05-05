package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Credit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CreditDao extends JpaRepository<Credit,Integer> {

    @Query(value = "SELECT c FROM Credit c ORDER BY c.id DESC")
    Page<Credit> findAll(Pageable of);

    @Query("SELECT c FROM Credit c WHERE c.companyId.number= :number")
    Credit findByCompanyNumber(@Param("number")String number);



}
