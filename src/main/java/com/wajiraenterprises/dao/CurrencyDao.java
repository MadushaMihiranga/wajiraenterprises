package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CurrencyDao extends JpaRepository<Currency,Integer> {

    @Query(value="SELECT new Currency (c.id,c.name,c.code,c.number) FROM Currency c")
    List<Currency> list();

    @Query("SELECT c FROM Currency c WHERE c.name= :name")
    Currency findByName(@Param("name")String name);

    @Query("SELECT c FROM Currency c WHERE c.code= :code")
    Currency findByCode(@Param("code")String code);

    @Query("SELECT c FROM Currency c WHERE c.number= :number")
    Currency findByNumber(@Param("number")String number);

}
