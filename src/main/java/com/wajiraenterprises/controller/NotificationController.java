package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.NotificationDao;
import com.wajiraenterprises.entity.Model;
import com.wajiraenterprises.entity.Notification;
import com.wajiraenterprises.entity.Notificationstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
public class NotificationController {

    @Autowired
    private NotificationDao notificationDao;

    @RequestMapping(value = "/notification/listbyemployee", method = RequestMethod.GET,params = "employeeId",produces = "application/json")
    public List<Notification> notificationListByEmployee(@RequestParam("employeeId") Integer employeeId) {
        return notificationDao.listByEmployee(employeeId);
    }

    @RequestMapping(value = "/notificationstatus/change", method = RequestMethod.GET,params = "notificationId",produces = "application/json")
    public Integer MarkAsRead(@RequestParam("notificationId") Integer notificationId) {
        try {
            System.out.println(notificationId);
            Notification notification = notificationDao.getOne(notificationId);
            Notificationstatus notificationstatus = new Notificationstatus(2);
            notification.setNotificationstatusId(notificationstatus);
            notification.setSeendate(LocalDate.now());
            notification.setSeentime(LocalTime.now());
            notificationDao.save(notification);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
            return 1;
        }
    }


}
