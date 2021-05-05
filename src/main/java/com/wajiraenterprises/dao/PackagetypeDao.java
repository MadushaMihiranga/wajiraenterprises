package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Packagetype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PackagetypeDao extends JpaRepository<Packagetype,Integer> {

    @Query(value="SELECT new Packagetype (p.id,p.name) FROM Packagetype p")
    List<Packagetype> list();

}
