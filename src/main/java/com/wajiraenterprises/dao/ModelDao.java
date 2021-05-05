package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModelDao extends JpaRepository<Model,Integer> {

    @Query(value="SELECT new Model (m.id,m.name) FROM Model m")
    List<Model> list();

    @Query(value="SELECT new Model (m.id,m.name) FROM Model m where m.brandId.id=:brandId")
    List<Model> listByBrand( @Param("brandId") Integer brandId);

    @Query("SELECT m FROM Model m WHERE m.name= :name")
    Model findByName(@Param("name")String name);



}
