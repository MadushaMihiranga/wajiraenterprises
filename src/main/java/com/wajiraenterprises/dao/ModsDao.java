package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Mods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModsDao extends JpaRepository<Mods,Integer> {

    @Query(value="SELECT new Mods (m.id,m.name) FROM Mods m")
    List<Mods> list();

}
