/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wajiraenterprises.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author madusha mihiranga
 */
@Entity
@Table(name = "vehiclerentelcompany")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vehiclerentelcompany.findAll", query = "SELECT v FROM Vehiclerentelcompany v")})
public class Vehiclerentelcompany implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "addres")
    private String addres;
    @Column(name = "contactnumber")
    private String contactnumber;
    @Column(name = "email")
    private String email;
    @Column(name = "adddate")
    //@Temporal(TemporalType.DATE)
    private LocalDate adddate;
    @OneToMany(mappedBy = "vehiclerentelcompanyId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Deliveryconfirmation> deliveryconfirmationList;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee employeeId;

    public Vehiclerentelcompany() {
    }

    public Vehiclerentelcompany(Integer id,String name) {
        this.id = id;
        this.name = name;
    }

    public Vehiclerentelcompany(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getAdddate() {
        return adddate;
    }

    public void setAdddate(LocalDate adddate) {
        this.adddate = adddate;
    }

    @XmlTransient
    public List<Deliveryconfirmation> getDeliveryconfirmationList() {
        return deliveryconfirmationList;
    }

    public void setDeliveryconfirmationList(List<Deliveryconfirmation> deliveryconfirmationList) {
        this.deliveryconfirmationList = deliveryconfirmationList;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
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
        if (!(object instanceof Vehiclerentelcompany)) {
            return false;
        }
        Vehiclerentelcompany other = (Vehiclerentelcompany) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Vehiclerentelcompany[ id=" + id + " ]";
    }
    
}
