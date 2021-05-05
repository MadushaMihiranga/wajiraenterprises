package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentDao extends JpaRepository <Payment,Integer>{

    @Query(value = "SELECT p FROM Payment p ORDER BY p.id DESC")
    Page<Payment> findAll(Pageable of);

    @Query(value="SELECT MAX (p.number) from Payment p")
    String numbermax();

}
