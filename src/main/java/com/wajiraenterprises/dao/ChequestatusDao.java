package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Chequestatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChequestatusDao extends JpaRepository<Chequestatus,Integer> {

    @Query(value="SELECT new Chequestatus (c.id,c.name) FROM Chequestatus c")
    List<Chequestatus> list();

}
