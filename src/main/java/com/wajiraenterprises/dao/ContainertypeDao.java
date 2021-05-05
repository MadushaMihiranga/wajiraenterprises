package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Containertype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContainertypeDao extends JpaRepository<Containertype,Integer> {

    @Query(value="SELECT new Containertype(c.id,c.name,c.description) FROM Containertype c")
    List<Containertype> list();

}
