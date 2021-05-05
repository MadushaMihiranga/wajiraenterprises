package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BankDao extends JpaRepository<Bank,Integer> {

    @Query(value="SELECT new Bank(b.id,b.name,b.code) FROM Bank b")
    List<Bank> list();

    @Query("SELECT b FROM Bank b WHERE b.name= :name")
    Bank findByName(@Param("name")String name);

    @Query("SELECT b FROM Bank b WHERE b.code= :code")
    Bank findByCode(@Param("code")String code);

}
