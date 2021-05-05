package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Customerstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerstatusDao extends JpaRepository<Customerstatus,Integer> {


    @Query(value="SELECT new Customerstatus(c.id,c.name) FROM Customerstatus c")
    List<Customerstatus> list();

}
