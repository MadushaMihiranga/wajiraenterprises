package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Country;
import com.wajiraenterprises.entity.Invoicestatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoicestatusDao extends JpaRepository<Invoicestatus,Integer> {

    @Query(value = "SELECT new Invoicestatus (i.id,i.name) FROM Invoicestatus i ")
    List<Invoicestatus> list();

}
