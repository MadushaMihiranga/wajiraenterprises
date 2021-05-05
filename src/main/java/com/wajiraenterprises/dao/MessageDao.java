package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageDao extends JpaRepository<Message,Integer> {
    //@Query(value = "SELECT new Message (m.)")
}