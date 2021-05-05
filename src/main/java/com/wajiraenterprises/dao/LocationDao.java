package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationDao extends JpaRepository<Location,Integer> {

    @Query(value="SELECT new Location(l.id,l.name) FROM Location l")
    List<Location> list();
}
