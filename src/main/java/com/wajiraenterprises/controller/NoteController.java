package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.NoteDao;
import com.wajiraenterprises.dao.UserDao;
import com.wajiraenterprises.entity.Note;
import com.wajiraenterprises.entity.Notestatus;
import com.wajiraenterprises.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
public class NoteController {

    @Autowired
    private NoteDao noteDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/stickynote", method = RequestMethod.POST)
    public String add(@CookieValue(value = "username", required = false) String username, @CookieValue(value = "password", required = false) String password, @Validated @RequestBody Note note) {

        try {
            note.setEmployeeId(userDao.findByUsername(username).getEmployeeId());
            note.setNotestatusId(new Notestatus(1));
            note.setDate(LocalDate.now());
            note.setTime(LocalTime.now());
            System.out.println("ok");
            noteDao.save(note);
            return "0";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error-Saving : " + e.getMessage();
        }

    }

    @RequestMapping(value = "/stickynote/listbyemployee", method = RequestMethod.GET,params = "employeeId",produces = "application/json")
    public List<Note> notificationListByEmployee(@RequestParam("employeeId") Integer employeeId) {
        return noteDao.listByEmployee(employeeId);
    }

}
