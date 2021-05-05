package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Clearingprocess;
import com.wajiraenterprises.entity.Clearingprocessstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClearingprocessstatusDao extends JpaRepository<Clearingprocessstatus,Integer> {

    @Query(value="SELECT new Clearingprocessstatus(c.id,c.name) FROM Clearingprocessstatus c")
    List<Clearingprocessstatus> list();

}
