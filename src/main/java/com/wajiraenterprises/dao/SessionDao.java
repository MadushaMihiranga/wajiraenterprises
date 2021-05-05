package com.wajiraenterprises.dao;


import com.wajiraenterprises.entity.Sessionlog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionDao extends JpaRepository<Sessionlog, Integer>
{

}