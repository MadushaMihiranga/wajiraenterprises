/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wajiraenterprises.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author madusha mihiranga
 */
@Entity
@Table(name = "notification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notification.findAll", query = "SELECT n FROM Notification n")})
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Column(name = "content")
    private String content;
    @Column(name = "date")
    //@Temporal(TemporalType.DATE)
    private LocalDate date;
    @Column(name = "time")
    //@Temporal(TemporalType.TIME)
    private LocalTime time;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee employeeId;
    @JoinColumn(name = "notificationstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Notificationstatus notificationstatusId;
    @JoinColumn(name = "notificationtype_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Notificationtype notificationtypeId;

    @Column(name = "seendate")
    //@Temporal(TemporalType.DATE)
    private LocalDate seendate;
    @Column(name = "seentime")
    //@Temporal(TemporalType.TIME)
    private LocalTime seentime;

    public Notification() {
    }

    public Notification(Integer id,String content, LocalDate date, LocalTime time, Employee employeeId, Notificationstatus notificationstatusId, Notificationtype notificationtypeId) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.time = time;
        this.employeeId = employeeId;
        this.notificationstatusId = notificationstatusId;
        this.notificationtypeId = notificationtypeId;
    }


    public LocalDate getSeendate() {
        return seendate;
    }

    public void setSeendate(LocalDate seendate) {
        this.seendate = seendate;
    }

    public LocalTime getSeentime() {
        return seentime;
    }

    public void setSeentime(LocalTime seentime) {
        this.seentime = seentime;
    }

    public Notification(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public Notificationstatus getNotificationstatusId() {
        return notificationstatusId;
    }

    public void setNotificationstatusId(Notificationstatus notificationstatusId) {
        this.notificationstatusId = notificationstatusId;
    }

    public Notificationtype getNotificationtypeId() {
        return notificationtypeId;
    }

    public void setNotificationtypeId(Notificationtype notificationtypeId) {
        this.notificationtypeId = notificationtypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notification)) {
            return false;
        }
        Notification other = (Notification) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Notification[ id=" + id + " ]";
    }
    
}
