package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Invetryitemstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvetryitemstatusDao extends JpaRepository <Invetryitemstatus,Integer> {

    @Query(value="SELECT new Invetryitemstatus (i.id,i.name) FROM Invetryitemstatus i")
    List<Invetryitemstatus> list();

}
