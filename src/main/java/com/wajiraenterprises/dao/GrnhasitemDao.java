package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Grnhasitem;
import com.wajiraenterprises.entity.Inventry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GrnhasitemDao extends JpaRepository<Grnhasitem,Integer> {



}
