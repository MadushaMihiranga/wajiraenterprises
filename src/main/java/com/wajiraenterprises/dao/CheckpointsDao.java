package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Checkpoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CheckpointsDao extends JpaRepository<Checkpoints,ItemDao> {

    @Query(value="SELECT new Checkpoints (c.id,c.name) FROM Checkpoints c")
    List<Checkpoints> list();

}
