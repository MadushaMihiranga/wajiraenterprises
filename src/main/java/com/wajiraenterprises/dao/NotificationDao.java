package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Model;
import com.wajiraenterprises.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationDao extends JpaRepository<Notification,Integer> {

    @Query(value="SELECT new Notification (n.id,n.content,n.date,n.time,n.employeeId,n.notificationstatusId,n.notificationtypeId) FROM Notification n WHERE n.employeeId.id =:employeeId ORDER BY n.notificationstatusId.id,n.id")
    List<Notification> listByEmployee(@Param("employeeId") Integer employeeId);


}
