package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Pestatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PestatusDao extends JpaRepository<Pestatus,Integer> {

    @Query(value="SELECT new Pestatus(p.id,p.name) FROM Pestatus p")
    List<Pestatus> list();

    @Query("SELECT p FROM Pestatus p WHERE p.name= :name")
    Pestatus findByName(@Param("name")String name);

}
