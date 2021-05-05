package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Note;
import com.wajiraenterprises.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteDao extends JpaRepository<Note,Integer> {

    @Query(value="SELECT new Note (n.id,n.title,n.text,n.date,n.color) FROM Note n WHERE n.employeeId.id =:employeeId ORDER BY n.date")
    List<Note> listByEmployee(@Param("employeeId") Integer employeeId);


}
