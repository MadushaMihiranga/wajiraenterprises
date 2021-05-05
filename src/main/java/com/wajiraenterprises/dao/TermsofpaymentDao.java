package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Termsofpayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TermsofpaymentDao extends JpaRepository<Termsofpayment,Integer> {

    @Query(value="SELECT new Termsofpayment(t.id,t.term,t.description) FROM Termsofpayment t")
    List<Termsofpayment> list();

}
