package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Activitytype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivitytypeDao extends JpaRepository<Activitytype,Integer> {

    @Query(value="SELECT new Activitytype (a.id,a.name) FROM Activitytype a")
    List<Activitytype> list();

    @Query("SELECT a FROM Activitytype a WHERE a.name= :name")
    Activitytype findByName(@Param("name")String name);


}
