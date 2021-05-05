package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Grn;
import com.wajiraenterprises.entity.Grnhasitem;
import com.wajiraenterprises.entity.Grnitemstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GrnitemstatusDao extends JpaRepository<Grnitemstatus,Integer> {

    @Query(value="SELECT new Grnitemstatus (g.id,g.name) FROM Grnitemstatus g")
    List<Grnitemstatus> list();



}
