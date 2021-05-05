package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaxDao extends JpaRepository<Tax,Integer> {

    @Query(value = "SELECT new Tax (t.id,t.code,t.description) FROM Tax t")
    List<Tax> list();
}
