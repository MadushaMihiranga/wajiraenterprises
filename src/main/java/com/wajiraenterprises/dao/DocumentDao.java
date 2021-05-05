package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentDao extends JpaRepository<Document,Integer> {

    @Query(value="SELECT new Document (d.id,d.name) FROM Document d")
    List<Document> list();

}
