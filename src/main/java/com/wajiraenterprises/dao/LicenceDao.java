package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Licence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LicenceDao extends JpaRepository<Licence,Integer> {

    @Query(value="SELECT new Licence (l.id,l.name) FROM Licence l")
    List<Licence> list();

}
