package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BrandDao extends JpaRepository<Brand,Integer> {

    @Query(value="SELECT new Brand(b.id,b.name) FROM Brand b")
    List<Brand> list();

    @Query("SELECT b FROM Brand b WHERE b.name= :name")
    Brand findByName(@Param("name")String name);

}
