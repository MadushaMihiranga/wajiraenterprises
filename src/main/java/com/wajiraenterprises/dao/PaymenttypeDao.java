package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Paymenttype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymenttypeDao extends JpaRepository<Paymenttype,Integer> {

    @Query(value="SELECT new Paymenttype (p.id,p.name) FROM Paymenttype p")
    List<Paymenttype> list();

}
