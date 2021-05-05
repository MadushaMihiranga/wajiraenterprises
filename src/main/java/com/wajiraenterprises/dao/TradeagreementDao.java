package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Tradeagreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TradeagreementDao extends JpaRepository<Tradeagreement,Integer> {

    @Query(value="SELECT new Tradeagreement (t.id ,t.code ,t.description) FROM Tradeagreement t")
    List<Tradeagreement> list();

}
