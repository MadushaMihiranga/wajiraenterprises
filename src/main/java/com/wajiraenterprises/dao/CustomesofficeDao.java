package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Customesoffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomesofficeDao extends JpaRepository<Customesoffice,ItemDao> {

    @Query(value = "SELECT new Customesoffice (c.id,c.name,c.code) FROM Customesoffice c")
    List<Customesoffice> list();
}
