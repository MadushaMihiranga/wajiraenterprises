package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Activitylog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ActivitylogDao extends JpaRepository<Activitylog,Integer> {

    @Query(value = "SELECT a FROM Activitylog a ORDER BY a.id DESC")
    Page<Activitylog> findAll(Pageable of);

}
