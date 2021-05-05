package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Customeracceptence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomeracceptenceDao extends JpaRepository<Customeracceptence,Integer> {

    @Query(value = "SELECT c FROM Customeracceptence c ORDER BY c.id DESC")
    Page<Customeracceptence> findAll(Pageable of);

}
