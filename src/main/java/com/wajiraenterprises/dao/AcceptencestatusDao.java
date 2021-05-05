package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Acceptencestatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AcceptencestatusDao extends JpaRepository<Acceptencestatus,Integer> {

    @Query(value="SELECT new Acceptencestatus (a.id,a.name) FROM Acceptencestatus a")
    List<Acceptencestatus> list();

}
