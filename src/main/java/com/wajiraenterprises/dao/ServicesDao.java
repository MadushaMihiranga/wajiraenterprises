package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServicesDao extends JpaRepository<Services,Integer> {

    @Query(value="SELECT new Services (s.id,s.name) FROM Services s")
    List<Services> list();



}
